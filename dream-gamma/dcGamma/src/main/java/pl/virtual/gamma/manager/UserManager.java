package pl.virtual.gamma.manager;

import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    @Getter private final Map<String, User> users = new ConcurrentHashMap<>();
    public boolean isPlayer(Player p) {
        return users.containsKey(p.getName());
    }

    public User getOrCreateUser(Player p) {
        if(isPlayer(p)) {
            return users.get(p.getName());
        }
        return create(p.getName());
    }

    public User create(String p) {
        Validate.notNull(p, "Player can't be null!");
        User u = new User(p);
        addUser(u);
        return u;
    }

    public void addUser(User user) {
        Validate.notNull(user, "User can't be null!");
        users.put(user.getName(), user);
    }
}
