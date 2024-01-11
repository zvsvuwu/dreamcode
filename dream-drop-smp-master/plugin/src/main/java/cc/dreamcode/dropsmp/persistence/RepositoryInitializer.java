package cc.dreamcode.dropsmp.persistence;

public interface RepositoryInitializer<V extends Repository> {

    V getRepositoryService();

}
