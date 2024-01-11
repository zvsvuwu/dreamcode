package cc.dreamcode.generator.features.generator.persistence;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;
import lombok.NonNull;
import org.bukkit.Location;

import java.util.Optional;
import java.util.UUID;

@DocumentCollection(path = "generators", keyLength = 36)
public interface GeneratorRepositoryCollection extends DocumentRepository<UUID, Generator> {

    default Generator get(@NonNull Location location, boolean create) {
        return this.findAll()
                .stream()
                .filter(generator -> generator.getLocation().equals(location))
                .findAny()
                .orElse(create ? this.findOrCreateByPath(UUID.randomUUID()) : null);
    }
}
