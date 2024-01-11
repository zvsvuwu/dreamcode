package cc.dreamcode.ffa.user;

import com.google.common.collect.Maps;
import org.bukkit.entity.HumanEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class UserCache {

    private final Map<UUID, User> userMap = Maps.newConcurrentMap();
    private final Map<String, UUID> nameToUuid = Maps.newConcurrentMap();

    /**
     * Adds a User to the cache.
     *
     * @param user User that is being to be added.
     */
    public void add(User user) {
        this.userMap.put(user.getUniqueId(), user);
        this.nameToUuid.put(user.getName().toLowerCase(), user.getUniqueId());
    }

    /**
     * Retrieves a User from the cache by their unique identifier.
     *
     * @param uniqueId Unique identifier of the User to retrieve.
     * @return User if found, otherwise null.
     */
    public User get(UUID uniqueId) {
        return this.userMap.get(uniqueId);
    }

    /**
     * Retrieves a User from the cache by their HumanEntity.
     *
     * @param humanEntity HumanEntity that represents the User to retrieve.
     * @return User if found, otherwise null.
     */
    public User get(HumanEntity humanEntity) {
        return get(humanEntity.getUniqueId());
    }

    /**
     * Retrieves a User from the cache by their name.
     *
     * @param name Name of the User to retrieve.
     * @return User if found, otherwise null.
     */
    public User get(String name) {
        UUID uniqueId = this.nameToUuid.get(name.toLowerCase());
        if (uniqueId == null) {
            return null;
        }
        return this.userMap.get(uniqueId);
    }

    /**
     * Removes a User from the cache.
     *
     * @param user User to remove.
     * @return True if the User was removed, otherwise false.
     */
    public boolean remove(User user) {
        return remove(user.getUniqueId());
    }

    /**
     * Removes a User from the cache by their unique identifier.
     *
     * @param uniqueId Unique identifier of the User to remove.
     * @return True if the User was removed, otherwise false.
     */
    private boolean remove(UUID uniqueId) {
        User user = this.userMap.get(uniqueId);
        if (user == null) {
            return false;
        }
        this.userMap.remove(uniqueId);
        this.nameToUuid.remove(user.getName().toLowerCase());
        return true;
    }

    /**
     * Removes a User from the cache by their name.
     *
     * @param name Name of the User to remove.
     * @return True if the User was removed, otherwise false.
     */
    private boolean remove(String name) {
        UUID uniqueId = this.nameToUuid.get(name.toLowerCase());
        if (uniqueId == null) {
            return false;
        }
        this.userMap.remove(uniqueId);
        this.nameToUuid.remove(name.toLowerCase());
        return true;
    }

    /**
     * Retrieves a Collection of all Users in the cache.
     *
     * @return Collection of Users.
     */
    public Collection<User> values() {
        return Collections.unmodifiableCollection(this.userMap.values());
    }
}