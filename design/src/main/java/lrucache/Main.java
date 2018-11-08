package lrucache;


import com.google.common.collect.TreeMultimap;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        LRUCache<Integer> lruCache = new LRUCacheImpl<>(2);
        Random random = new Random();
        int[] arr = {1,2,3,4,1,5,3,4,6,7};
        for(int a : arr){
            lruCache.refer(a);
            lruCache.printCurrentCache();
        }
    }
}
