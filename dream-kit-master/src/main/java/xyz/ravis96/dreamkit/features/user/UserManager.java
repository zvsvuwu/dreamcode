package xyz.ravis96.dreamkit.features.user;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.UUID;

@DocumentCollection(path = "users", keyLength = 36)
public interface UserManager extends DocumentRepository<UUID, User> {

    default Optional<User> findByName(String name) {
        for (User user : this.findAll()) {
            if(user.getName().equals(name)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    default User getOrCreate(OfflinePlayer player) {
        User user = this.findOrCreateByPath(player.getUniqueId());
        if (player.getName() != null) user.setName(player.getName());

        return user;
    }
}
