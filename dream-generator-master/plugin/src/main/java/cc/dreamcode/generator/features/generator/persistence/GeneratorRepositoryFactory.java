package cc.dreamcode.generator.features.generator.persistence;

import cc.dreamcode.generator.PluginMain;
import cc.dreamcode.generator.persistence.PersistenceHandler;
import cc.dreamcode.generator.persistence.RepositoryInitializer;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeneratorRepositoryFactory implements RepositoryInitializer<GeneratorRepository> {

    private final PluginMain plugin;
    private final PersistenceHandler persistenceHandler;

    @Override
    public GeneratorRepository getRepositoryService() {
        PersistenceCollection persistenceCollection = PersistenceCollection.of(GeneratorRepositoryCollection.class);

        this.persistenceHandler.registerCollection(persistenceCollection, true);

        GeneratorRepository generatorRepository = new GeneratorRepository(
                RepositoryDeclaration.of(GeneratorRepositoryCollection.class)
                        .newProxy(
                                this.persistenceHandler.getDatabasePersistence(),
                                persistenceCollection,
                                this.plugin.getClass().getClassLoader()
                        ),
                RepositoryDeclaration.of(GeneratorRepositoryCollection.class)
                        .newProxy(
                                this.persistenceHandler.getInMemoryPersistence(),
                                persistenceCollection,
                                this.plugin.getClass().getClassLoader()
                        )
        );

        this.persistenceHandler.getRepositoryLoaderList().add(generatorRepository.getRepositoryLoader());

        return generatorRepository;
    }
}