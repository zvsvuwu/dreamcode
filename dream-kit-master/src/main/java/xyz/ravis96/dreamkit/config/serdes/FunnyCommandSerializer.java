package xyz.ravis96.dreamkit.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import xyz.ravis96.dreamkit.config.subconfig.CommandsConfig;

public class FunnyCommandSerializer implements ObjectSerializer<CommandsConfig.FunnyCommand> {
    @Override
    public boolean supports(Class<? super CommandsConfig.FunnyCommand> type) {
        return CommandsConfig.FunnyCommand.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(CommandsConfig.@NonNull FunnyCommand object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("aliases", object.getAliases());
        data.add("description", object.getDescription());
        data.add("enabled", object.isEnabled());
    }

    @Override
    public CommandsConfig.FunnyCommand deserialize(DeserializationData data, GenericsDeclaration generics) {
        return new CommandsConfig.FunnyCommand(data.get("name", String.class),
                data.getAsList("aliases", String.class),
                data.get("description", String.class),
                data.get("enabled", Boolean.class));
    }
}
