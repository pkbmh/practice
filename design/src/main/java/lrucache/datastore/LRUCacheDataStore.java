package lrucache.datastore;

import list.DLL;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LRUCacheDataStore<V> implements LRUDataStore<V> {
    private final int capacity;
    private final Map<V, DLL.Node> kvMap;
    private final DLL<V> data;

    public LRUCacheDataStore(int capacity) {
        this.capacity = capacity;
        this.data = new DLL<>();
        kvMap = new HashMap<>();
    }

    @Override
    public boolean put(V key, V value) {
        DLL.Node node = data.addAndGetPointer(value);
        kvMap.put(key, node);
        return true;
    }

    @Override
    public V get(V key) {
        DLL.Node<V> node = kvMap.get(key);
        if(node != null) {
            System.out.println("Item : " + key + " is present in the cache");
            V item = node.getItem();
            data.makeHead(node);
            return item;
        }
        // Item is not present in the cache
        cacheItem(key);
        return key;
    }

    private void cacheItem(V item){
        System.out.println("Item : " + item + " is not present in the cache, caching item");
        if(data.size() < capacity) {
            System.out.println("data store is not full: size : " + data.size());
            put(item, item);
        }else {
            System.out.println("data store is full size: " + data.size());
            V tail = data.removeTail();
            kvMap.remove(tail);
            put(item, item);
        }
    }

    @Override
    public boolean containsKey(V key) {
        return kvMap.containsKey(key);
    }

    @Override
    public V remove(V key) {
        DLL.Node<V> node = kvMap.get(key);
        if(node != null) {
            data.remove(node);
            return node.getItem();
        }
        return null;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void printCurrentCache() {
        Iterator<V> it = data.iterator();
        while (it.hasNext()) {
            System.out.println("Item: " + it.next());
        }
    }
}
