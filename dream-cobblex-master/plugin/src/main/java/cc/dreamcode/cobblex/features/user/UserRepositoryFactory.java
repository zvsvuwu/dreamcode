package cc.dreamcode.cobblex.features.user;

import cc.dreamcode.cobblex.PluginMain;
import cc.dreamcode.cobblex.persistence.PersistenceHandler;
import cc.dreamcode.cobblex.persistence.RepositoryInitializer;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryFactory implements RepositoryInitializer<UserRepository> {

    private final PluginMain plugin;
    private final PersistenceHandler persistenceHandler;

    @Override
    public UserRepository getRepositoryService() {
        PersistenceCollection persistenceCollection = PersistenceCollection.of(UserRepositoryCollection.class);

        this.persistenceHandler.registerCollection(persistenceCollection, true);

        UserRepository userRepository = new UserRepository(
                RepositoryDeclaration.of(UserRepositoryCollection.class)
                        .newProxy(
                                this.persistenceHandler.getDatabasePersistence(),
                                persistenceCollection,
                                this.plugin.getClass().getClassLoader()
                        ),
                RepositoryDeclaration.of(UserRepositoryCollection.class)
                        .newProxy(
                                this.persistenceHandler.getInMemoryPersistence(),
                                persistenceCollection,
                                this.plugin.getClass().getClassLoader()
                        )
        );

        this.persistenceHandler.getRepositoryLoaderList().add(userRepository.getRepositoryLoader());

        return userRepository;
    }
}
