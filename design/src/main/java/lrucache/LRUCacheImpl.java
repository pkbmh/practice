package lrucache;

import lrucache.datastore.LRUCacheDataStore;
import lrucache.datastore.LRUDataStore;

public class LRUCacheImpl<V> implements LRUCache<V> {
    private final LRUDataStore<V> dataStore;
    public LRUCacheImpl(int capacity){
        dataStore = new LRUCacheDataStore(capacity);
    }

    public int getCapacity() {
        return dataStore.getCapacity();
    }

    @Override
    public V refer(V item) {
        return dataStore.get(item);
    }

    @Override
    public void printCurrentCache() {
        dataStore.printCurrentCache();
    }
}
