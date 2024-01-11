package cc.dreamcode.generator.features.generator.persistence;

import cc.dreamcode.generator.features.generator.template.GeneratorTemplate;
import cc.dreamcode.generator.persistence.Repository;
import cc.dreamcode.generator.persistence.RepositoryLoader;
import eu.okaeri.persistence.repository.DocumentRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GeneratorRepository implements Repository<Location, Generator> {

    @Getter private final GeneratorRepositoryCollection generatorRepositoryCollection;

    @Getter private final GeneratorRepositoryCollection generatorCacheRepository;
    @Override
    public RepositoryLoader<UUID, Generator> getRepositoryLoader() {
        return new RepositoryLoader<UUID, Generator>() {
            @Override
            public DocumentRepository<UUID, Generator> getDatabaseRepository() {
                return generatorRepositoryCollection;
            }
            @Override
            public DocumentRepository<UUID, Generator> getCacheRepository() {return generatorCacheRepository;}
            @Override
            public List<Field> getDocumentFields() {
                return Arrays.stream(Generator.class.getDeclaredFields()).collect(Collectors.toList());
            }
        };
    }

    @Override
    public boolean contains(Location location) {
        return this.generatorCacheRepository.findAll()
                .stream()
                .anyMatch(generator -> generator.getLocation().equals(location));
    }

    @Override
    public Optional<Generator> getByKey(Location location) {
        return this.generatorCacheRepository.findAll()
                .stream()
                .filter(generator -> generator.getLocation().equals(location))
                .findAny();
    }

    @Override
    public Set<Generator> getSet() {
        return new HashSet<>(this.generatorCacheRepository.findAll());
    }

    @Override
    public Generator get(Location location, boolean create) {
        return this.generatorCacheRepository.get(location, create);
    }

    @Override
    public void remove(Location location) {
        List<UUID> uuids = this.generatorCacheRepository.findAll()
                .stream()
                .filter(generator -> generator.getLocation().equals(location))
                .map(Generator::getUniqueId)
                .collect(Collectors.toList());

        uuids.forEach(this.generatorCacheRepository::deleteByPath);
    }

    public void createByTemplate(@NonNull GeneratorTemplate generatorTemplate, @NonNull Location location) {
        final Generator generator = this.generatorCacheRepository.get(location, true);

        generator.setRegenerationSpeed(generatorTemplate.getRegenerationSpeed());
        generator.setLocation(location);

        generator.save();
    }

}
