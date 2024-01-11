package cc.dreamcode.cobblex.persistence;

public interface RepositoryInitializer<V extends Repository> {

    V getRepositoryService();

}
