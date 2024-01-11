package pl.virutal.mobs.manager;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import pl.virutal.mobs.main.Main;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private final Map<String, User> users = new ConcurrentHashMap<>();
    public Set<User> getUsers() { return new HashSet<>(users.values()); }
    public boolean isPlayer(Player p) {
        return users.containsKey(p.getName());
    }

    public User getOrCreateUser(Player p) {
        if(isPlayer(p)) {
            return users.get(p.getName());
        }
        return create(p);
    }

    public User create(Player p) {
        Validate.notNull(p, "Player can't be null!");
        User u = new User(p.getUniqueId(), p.getName());
        addUser(u);
        return u;
    }

    public void addUser(User user) {
        Validate.notNull(user, "User can't be null!");
        users.put(user.getName(), user);
    }
}
