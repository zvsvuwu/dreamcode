package cc.dreamcode.guisuffix.user;

import com.google.common.collect.Maps;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class UserCache {

    private final Map<UUID, User> userMap = Maps.newConcurrentMap();

    private final Map<String, UUID> nameToUuid = Maps.newConcurrentMap();

    public void add(@NonNull User user) {
        this.userMap.put(user.getUniqueId(), user);
        this.nameToUuid.put(user.getName().toLowerCase(), user.getUniqueId());
    }

    public User get(@NonNull HumanEntity humanEntity) {
        return get(humanEntity.getUniqueId());
    }

    public User get(@NonNull UUID uuid) {
        return this.userMap.get(uuid);
    }

    public User get(@NonNull String name) {
        UUID uniqueId = this.nameToUuid.get(name.toLowerCase());

        if (uniqueId == null) {
            return null;
        }
        
        return this.userMap.get(uniqueId);
    }

    public Collection<User> values() {
        return Collections.unmodifiableCollection(this.userMap.values());
    }
}
