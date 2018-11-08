package lrucache.datastore;

public interface LRUDataStore<V> extends KVDataStore<V, V> {
    void printCurrentCache();
}
