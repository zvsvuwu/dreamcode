package cc.dreamcode.nagroda.content;

import cc.dreamcode.nagroda.NagrodaPlugin;
import cc.dreamcode.nagroda.config.MessageConfig;
import cc.dreamcode.nagroda.config.PluginConfig;
import cc.dreamcode.nagroda.model.user.User;
import cc.dreamcode.nagroda.model.user.UserRepository;
import cc.dreamcode.nagroda.notice.NoticeSender;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NagrodaListener extends ListenerAdapter implements NoticeSender {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject NagrodaManager nagrodaManager;
    private @Inject UserRepository userRepository;

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getButton().getId().equals("reward-button")) return;

        TextInput code = TextInput.create("code-input", pluginConfig.nagrodaConfig.moduleInputName, TextInputStyle.SHORT)
                .setMinLength(1)
                .setMaxLength(3)
                .setRequired(true)
                .setPlaceholder(pluginConfig.nagrodaConfig.modulePlaceholder)
                .build();

        Modal modal = Modal.create("reward-modal", pluginConfig.nagrodaConfig.moduleTitle).addActionRow(code).build();
        event.replyModal(modal).queue(null,
                new ErrorHandler().ignore(Arrays.asList(ErrorResponse.values())));
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if (!event.getModalId().equals("reward-modal")) return;
        String input = event.getValue("code-input").getAsString();
        for (char c : input.toCharArray()) if (!Character.isDigit(c)) {
            event.reply(messageConfig.codeContainsLetters).setEphemeral(true).queue(null,
                    new ErrorHandler().ignore(Arrays.asList(ErrorResponse.values())));
            return;
        }

        int code = Integer.parseInt(input);
        if (code <= 0) return;

        if (!nagrodaManager.codes.containsValue(code)) {
            event.reply(messageConfig.codeDoesntExist).setEphemeral(true).queue(null,
                    new ErrorHandler().ignore(Arrays.asList(ErrorResponse.values())));
            return;
        }

        UUID uuid = UUID.fromString(nagrodaManager.getKey(nagrodaManager.codes, code).toString());
        Player p = NagrodaPlugin.getNagrodaPlugin().getServer().getPlayer(uuid);
        if (p == null) {
            event.reply(messageConfig.playerOffline).setEphemeral(true).queue(null,
                    new ErrorHandler().ignore(Arrays.asList(ErrorResponse.values())));
            return;
        }

        try {
            CompletableFuture<User> user = userRepository.findOrCreate(uuid, p.getName());
            user.get().setRewardReceived(true);
            user.get().saveAsync();

            NagrodaPlugin.getNagrodaPlugin().getServer().getScheduler().runTask(NagrodaPlugin.getNagrodaPlugin(), () ->
                    NagrodaPlugin.getNagrodaPlugin().getServer().dispatchCommand(NagrodaPlugin.getNagrodaPlugin().getServer().getConsoleSender(),
                            pluginConfig.nagrodaConfig.command
                                    .replace("{gracz}", p.getName())));
            nagrodaManager.codes.remove(nagrodaManager.getKey(nagrodaManager.codes, code));
            event.reply(messageConfig.receivedReward).setEphemeral(true).queue(null,
                    new ErrorHandler().ignore(Arrays.asList(ErrorResponse.values())));

            if (pluginConfig.nagrodaConfig.broadcastReward) {
                send(messageConfig.broadcastReward, p, new ImmutableMap.Builder<String, Object>()
                        .put("gracz", p.getName())
                        .build());
            }
        } catch (IllegalArgumentException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
