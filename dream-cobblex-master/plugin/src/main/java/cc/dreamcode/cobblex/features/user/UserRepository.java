package cc.dreamcode.cobblex.features.user;

import cc.dreamcode.cobblex.persistence.Repository;
import cc.dreamcode.cobblex.persistence.RepositoryLoader;
import eu.okaeri.persistence.repository.DocumentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserRepository implements Repository<OfflinePlayer, User> {

    @Getter private final UserRepositoryCollection userDatabaseRepository;
    @Getter private final UserRepositoryCollection userCacheRepository;

    @Override
    public RepositoryLoader<UUID, User> getRepositoryLoader() {
        return new RepositoryLoader<UUID, User>() {
            @Override
            public DocumentRepository<UUID, User> getDatabaseRepository() {
                return userDatabaseRepository;
            }

            @Override
            public DocumentRepository<UUID, User> getCacheRepository() {
                return userCacheRepository;
            }

            @Override
            public List<Field> getDocumentFields() {
                return Arrays.stream(User.class.getDeclaredFields()).collect(Collectors.toList());
            }
        };
    }

    @Override
    public boolean contains(OfflinePlayer offlinePlayer) {
        return this.userCacheRepository.existsByPath(offlinePlayer.getUniqueId());
    }

    @Override
    public Optional<User> getByKey(OfflinePlayer offlinePlayer) {
        return this.userCacheRepository.findByPath(offlinePlayer.getUniqueId());
    }

    @Override
    public Set<User> getSet() {
        return new HashSet<>(this.userCacheRepository.findAll());
    }

    @Override
    public User get(OfflinePlayer offlinePlayer, boolean createWhenNull) {
        if (createWhenNull) {
            return this.userCacheRepository.getOrCreate(offlinePlayer);
        }

        return this.userCacheRepository.get(offlinePlayer);
    }

    @Override
    public void remove(OfflinePlayer offlinePlayer) {
        this.userCacheRepository.deleteByPath(offlinePlayer.getUniqueId());
    }
}
