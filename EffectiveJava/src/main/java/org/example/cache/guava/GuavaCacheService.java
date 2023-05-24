package org.example.cache.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import java.util.concurrent.TimeUnit;

public class GuavaCacheService<K,V> {
    private final Cache<K, V> cache;
    private final RemovalListener<K, V> removalListener;

    public GuavaCacheService(int maxSize, long expirationTimeInSecs) {
        this.removalListener = notification -> {
            K key = notification.getKey();
            V value = notification.getValue();
            System.out.println("Evicted key=" + key + ", value=" + value);
        };
        cache = buildCache(maxSize, expirationTimeInSecs);
    }

    public GuavaCacheService(int maxSize, long expirationTimeInSecs, RemovalListener<K, V> listener) {
        this.removalListener = listener;
        cache = buildCache(maxSize, expirationTimeInSecs);
    }

    public V get(K key) {
        return getCache().getIfPresent(key);
    }

    public void put(K key, V value) {
        getCache().put(key, value);
    }

    public long getEvictions() {
        return getCache().stats().evictionCount();
    }

    public double getAveragePutTime() {
        return getCache().stats().averageLoadPenalty() / TimeUnit.MILLISECONDS.toNanos(1); // convert nanoseconds to milliseconds
    }

    public Cache<K, V> getCache() {
        return cache;
    }

    private Cache<K, V> buildCache(int maxSize, long expirationTimeInSecs) {
        return CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterAccess(expirationTimeInSecs, TimeUnit.SECONDS)
                .removalListener(removalListener)
                .build();
    }
}
