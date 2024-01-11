package cc.dreamcode.restarts.persistence;

import cc.dreamcode.restarts.RestartLogger;
import cc.dreamcode.restarts.RestartPlugin;
import cc.dreamcode.restarts.features.Manager;
import eu.okaeri.persistence.document.Document;
import eu.okaeri.persistence.repository.DocumentRepository;
import lombok.NonNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Service for InMemory synchronization with database. (addon for DocumentRepository)
 * @param <K> PersistencePath
 * @param <V> Document
 */
public abstract class PersistenceService<K, V extends Document> implements Manager<K, V> {

    private final Map<K, V> inMemoryDocumentMap = new HashMap<>();
    public abstract DocumentRepository<K, V> getDocumentRepository();

    @Override
    public Optional<V> getByKey(K k) {
        return Optional.ofNullable(this.inMemoryDocumentMap.get(k));
    }

    @Override
    public Set<V> getSet() {
        return new HashSet<>(this.inMemoryDocumentMap.values());
    }

    @SuppressWarnings("unchecked")
    @Override
    public V add(V v) {
        CompletableFuture.runAsync(() ->
                this.getDocumentRepository().findOrCreateByPath((K) v.getPath().getValue()));

        return this.inMemoryDocumentMap.put((K) v.getPath().getValue(), v);
    }

    @Override
    public void remove(K k) {
        this.inMemoryDocumentMap.remove(k);

        CompletableFuture.runAsync(() ->
                this.getDocumentRepository().deleteByPath(k));
    }

    public void load(@NonNull K k) {
        CompletableFuture.runAsync(() ->
                this.getDocumentRepository().findByPath(k).ifPresent(ob ->
                        this.inMemoryDocumentMap.put(k, ob)));
    }

    @SuppressWarnings("unchecked")
    public void loadAllToMemory() {
        long start = System.currentTimeMillis();

        this.getDocumentRepository().findAll().forEach(v ->
                this.inMemoryDocumentMap.put((K) v.getPath().getValue(), v));

        long took = System.currentTimeMillis() - start;
        RestartPlugin.getRestartLogger().info(
                new RestartLogger.Loader()
                        .type("Loaded repository content")
                        .name(this.getDocumentRepository().getDocumentType().getSimpleName() + "s")
                        .took(took)
                        .build()
        );
    }
}
