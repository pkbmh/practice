package lrucache.datastore;

public interface KVDataStore<K,V> extends Cache<K, V> {
    int getCapacity();
}
