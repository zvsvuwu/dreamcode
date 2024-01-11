package xyz.ravis96.dreamkit.commands.completers;

import lombok.RequiredArgsConstructor;
import net.dzikoysk.funnycommands.commands.CommandUtils;
import net.dzikoysk.funnycommands.resources.Completer;
import net.dzikoysk.funnycommands.resources.Context;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import xyz.ravis96.dreamkit.config.subconfig.KitConfig;
import xyz.ravis96.dreamkit.features.kit.Kit;

import java.util.ArrayList;
import java.util.List;

@FunnyComponent
@RequiredArgsConstructor
public class KitCompleter implements Completer {

    private final KitConfig kitConfig;

    @Override
    public String getName() {
        return "kits";
    }

    @Override
    public List<String> apply(Context first, String second, Integer third) {
        return CommandUtils.collectCompletions(this.kitConfig.kitList, second, third, ArrayList::new, Kit::getName);
    }
}
