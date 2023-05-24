package org.example.cache.simple;

import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CacheService<K, V> {
    private final int maxSize;
    private final EvictionPolicy policy;
    private final ConcurrentMap<K, CacheEntry> cache;
    private final ConcurrentLinkedQueue<K> lruQueue;
    private final PriorityQueue<CacheEntry> lfuQueue;
    private final ScheduledExecutorService scheduler;
    private final RemovalListener<K, V> removalListener;
    private final AtomicInteger evictions;
    private final AtomicLong putTime;
    private final long maxDurationInMillis;

    public CacheService(long maxDurationInMillis, int maxSize, EvictionPolicy policy, RemovalListener<K, V> removalListener) {
        this.maxDurationInMillis = maxDurationInMillis;
        this.maxSize = maxSize;
        this.policy = policy;
        this.cache = new ConcurrentHashMap<>();
        this.lruQueue = new ConcurrentLinkedQueue<>();
        this.lfuQueue = new PriorityQueue<>(Comparator.comparingInt(CacheEntry::getFrequency));
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.removalListener = removalListener;
        this.evictions = new AtomicInteger();
        this.putTime = new AtomicLong();

        scheduleEvictionTask();
    }

    public V get(K key) {
        CacheEntry entry = cache.get(key);
        if (entry != null) {
            entry.updateAccessTime();
            if (policy == EvictionPolicy.LFU) {
                lfuQueue.remove(entry);
                entry.incrementFrequency();
                lfuQueue.offer(entry);
            }
            return entry.value();
        }
        return null;
    }

    public void put(K key, V value) {
        CacheEntry entry = new CacheEntry(key, value);
        CacheEntry previous = cache.put(key, entry);
        if (previous != null) {
            if (policy == EvictionPolicy.LFU) {
                lfuQueue.remove(previous);
            } else if (policy == EvictionPolicy.LRU) {
                lruQueue.remove(key);
            }
        } else {
            updatePutTime(key, value);
        }
        if (cache.size() > maxSize) {
            evict();
        }
        if (policy == EvictionPolicy.LFU) {
            lfuQueue.offer(entry);
        } else if (policy == EvictionPolicy.LRU) {
            lruQueue.offer(key);
        }
    }

    private void evict() {
        K key = null;
        if (policy == EvictionPolicy.LFU) {
            CacheEntry entry = lfuQueue.poll();
            if (entry != null) {
                key = entry.key();
            }
        } else if (policy == EvictionPolicy.LRU) {
            key = lruQueue.poll();
        }
        if (key != null) {
            CacheEntry entry = cache.remove(key);
            if (entry != null) {
                removalListener.onRemoval(RemovalNotification.create(entry.key(), entry.value(), RemovalCause.SIZE));
            }
            evictions.incrementAndGet();
        }
    }

    private void scheduleEvictionTask() {
        scheduler.scheduleWithFixedDelay(this::evictExpiredEntries, 0, 5, TimeUnit.SECONDS);
    }

    private void evictExpiredEntries() {
        for (CacheEntry entry : cache.values()) {
            if (entry.isExpired(maxDurationInMillis)) {
                cache.remove(entry.key(), entry);
                removalListener.onRemoval(RemovalNotification.create(entry.key(), entry.value(), RemovalCause.EXPIRED));
                evictions.incrementAndGet();
            }
        }
    }

    /**
     * The purpose of this method is to update the putTime atomic counter each time a new item is added to the cache to calculate the average time spent putting new values into the cache.
     * @param key
     * @param value
     */
    private void updatePutTime(K key, V value) {
        long start = System.nanoTime();
        cache.put(key, new CacheEntry(key, value));
        putTime.addAndGet(System.nanoTime() - start);
    }

    public int getEvictions() {
        return evictions.get();
    }

    public double getAveragePutTime() {
        long puts = cache.size() < maxSize ? cache.size() + 1 : cache.size();
        return (double) putTime.get() / puts;
    }

    private class CacheEntry implements Comparable<CacheEntry> {
        private final K key;
        private final V value;
        private int frequency;
        private long lastAccessedTime;

        public CacheEntry(K key, V value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
            this.lastAccessedTime = System.currentTimeMillis();
        }

        public K key() {
            return key;
        }

        public V value() {
            return value;
        }

        public int getFrequency() {
            return frequency;
        }

        public void incrementFrequency() {
            frequency++;
        }

        public void updateAccessTime() {
            this.lastAccessedTime = System.currentTimeMillis();
        }

        @Override
        public int compareTo(CacheEntry cacheEntry) {
            return Long.compare(this.lastAccessedTime, cacheEntry.lastAccessedTime);
        }

        public boolean isExpired(long maxDurationInMillis) {
            return System.currentTimeMillis() - lastAccessedTime > maxDurationInMillis;
        }
    }
}
