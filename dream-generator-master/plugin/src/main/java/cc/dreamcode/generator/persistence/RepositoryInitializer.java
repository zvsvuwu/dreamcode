package cc.dreamcode.generator.persistence;

public interface RepositoryInitializer<V extends Repository> {

    V getRepositoryService();

}
