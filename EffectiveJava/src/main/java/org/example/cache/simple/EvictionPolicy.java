package org.example.cache.simple;

/**
 * This enum defines two eviction policies: LFU and LRU
 * LFU stands for Least Frequently Used and LRU stands for Least Recently Used.
 * These are common replacement policies used in cache management systems.
 *
 * LFU policy replaces the least frequently used item in the cache when the cache is full and a new item needs to be added.
 * This policy is based on the assumption that an item that has been accessed less frequently is less likely to be accessed again in the future,
 * so it can be removed from the cache without affecting performance significantly.
 *
 * LRU policy replaces the least recently used item in the cache when the cache is full and a new item needs to be added.
 * This policy is based on the assumption that an item that has not been accessed for a long time is less likely to be accessed again in the near future,
 * so it can be removed from the cache without affecting performance significantly.
 *
 */
public enum EvictionPolicy {

    LFU(1), LRU(2);

    private int value;
    EvictionPolicy(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
