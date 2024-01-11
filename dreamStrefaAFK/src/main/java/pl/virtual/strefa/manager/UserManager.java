package pl.virtual.strefa.manager;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private final Map<String, User> users = new ConcurrentHashMap<>();
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
        User u = new User(p.getUniqueId(), p.getName());
        addUser(u);
        return u;
    }

    public void addUser(User user) {
        users.put(user.getName(), user);
    }

    public void resetTimer(User user){
        user.setTime(System.currentTimeMillis()/1000L);
    }
}
