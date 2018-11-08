package lrucache;

public interface LRUCache<V> {
    V refer(V item);
    void printCurrentCache();
}
