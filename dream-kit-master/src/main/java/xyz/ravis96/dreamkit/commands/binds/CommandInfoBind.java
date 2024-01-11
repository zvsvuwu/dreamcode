package xyz.ravis96.dreamkit.commands.binds;

import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.commands.CommandUtils;
import net.dzikoysk.funnycommands.resources.Bind;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.panda_lang.utilities.inject.Resources;

@FunnyComponent
public class CommandInfoBind implements Bind {
    @Override
    public void accept(Resources resources) {
        resources.on(CommandInfo.class).assignHandler((property, annotation, objects) ->
                CommandUtils.getContext(objects).getCommandStructure().getMetadata().getCommandInfo());
    }
}
