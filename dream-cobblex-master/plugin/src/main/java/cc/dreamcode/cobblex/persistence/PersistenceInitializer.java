package cc.dreamcode.cobblex.persistence;

import eu.okaeri.persistence.repository.DocumentRepository;

public interface PersistenceInitializer<V extends DocumentRepository> {

    V getRepositoryService();

}
