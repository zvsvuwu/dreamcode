package xyz.ravis96.dreamkit.features;

import java.util.Set;

public interface IManager<K, V> {

    V getByKey(K k);

    Set<V> getSet();

    void add(V t);

    void remove(K k);

}
