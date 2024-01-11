package cc.dreamcode.ffa.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;

import java.util.AbstractMap.SimpleEntry;
import java.util.Optional;
import java.util.UUID;

/**
 * This interface defines the operations for managing User data in a data repository.
 * It uses a DocumentRepository provided by Okaeri's Persistence to handle the actual storage.
 * * @author trueman96
 * @version 1.0-beta.1
 * @since 2023-11-24
 */
@DocumentCollection(path = "user", keyLength = 36)
public interface UserRepository extends DocumentRepository<UUID, User> {

    /**
     * Finds a User by UUID. If no User is found, a new User with the given UUID and username is created and stored.
     *
     * @param uuid         The UUID of the User.
     * @param userName     The username of the User.
     * @return             Entry with boolean if user was found or created and the User.
     */
    default SimpleEntry<Boolean, User> findOrCreate(@NonNull UUID uuid, String userName) {
        boolean wasFound = this.findByPath(uuid).isPresent();
        User user = this.findOrCreateByPath(uuid);
        if (userName != null) {
            user.setName(userName);
        }
        return new SimpleEntry<>(wasFound, user);
    }

    /**
     * Finds a User by UUID. If no User is found, a new User with the given UUID is created and stored.
     *
     * @param uuid         The UUID of the User.
     * @return             Entry with boolean if user was found or created and the User.
     */
    default SimpleEntry<Boolean, User> findOrCreateByUUID(@NonNull UUID uuid) {
        return this.findOrCreate(uuid, null);
    }

    /**
     * Finds a User by their username. If no User is found, an empty Optional is returned.
     *
     * @param name         The username of the User.
     * @param ignoreCase   If true, the search is case-insensitive. Otherwise, it is case-sensitive.
     * @return             An Optional containing the found User, or an empty Optional if no User is found.
     */
    default Optional<User> findByName(@NonNull String name, boolean ignoreCase) {
        return this.streamAll()
                .filter(user -> ignoreCase
                        ? user.getName().equalsIgnoreCase(name)
                        : user.getName().equals(name))
                .findFirst();
    }
}
