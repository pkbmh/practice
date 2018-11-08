package lrucache.datastore;

public interface Cache<K,V> {
    boolean put(K key, V value);
    V get(K key);
    boolean containsKey(K key);
    V remove(K key);
}
