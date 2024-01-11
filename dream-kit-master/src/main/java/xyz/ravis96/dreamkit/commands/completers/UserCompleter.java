package xyz.ravis96.dreamkit.commands.completers;

import lombok.RequiredArgsConstructor;
import net.dzikoysk.funnycommands.commands.CommandUtils;
import net.dzikoysk.funnycommands.resources.Completer;
import net.dzikoysk.funnycommands.resources.Context;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import xyz.ravis96.dreamkit.features.user.User;
import xyz.ravis96.dreamkit.features.user.UserManager;

import java.util.ArrayList;
import java.util.List;

@FunnyComponent
@RequiredArgsConstructor
public class UserCompleter implements Completer {

    private final UserManager userManager;

    @Override
    public String getName() {
        return "users";
    }

    @Override
    public List<String> apply(Context first, String second, Integer third) {
        return CommandUtils.collectCompletions(this.userManager.findAll(), second, third, ArrayList::new, User::getName);
    }
}
