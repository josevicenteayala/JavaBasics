package org.example.cache.guava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.cache.RemovalListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuavaCacheServiceTest {

    private GuavaCacheService<String, Integer> guavaCacheService;

    @BeforeEach
    void setUp() {
        guavaCacheService = new GuavaCacheService<>(2, 5);
    }

    @Test
    void shouldPutAndGetFromCache() {
        // Given
        String key = "one";
        Integer value = 1;

        // When
        guavaCacheService.put(key, value);

        // Then
        assertEquals(value, guavaCacheService.get(key));
    }

    @Test
    void shouldEvictOldestEntryWhenMaxSizeReached() {
        // Given
        String key1 = "one";
        Integer value1 = 1;
        String key2 = "two";
        Integer value2 = 2;
        String key3 = "three";
        Integer value3 = 3;

        // When
        guavaCacheService.put(key1, value1);
        guavaCacheService.put(key2, value2);
        guavaCacheService.put(key3, value3);

        // Then
        assertNull(guavaCacheService.get(key1));
        assertEquals(value2, guavaCacheService.get(key2));
        assertEquals(value3, guavaCacheService.get(key3));
    }

    @Test
    void shouldInvokeRemovalListenerOnEviction() {
        // Given
        String key = "one";
        Integer value = 1;
        String key2 = "two";
        Integer value2 = 2;
        String[] actual = new String[1];
        RemovalListener<String, Integer> listener = notification -> {
            actual[0] = "key=" + notification.getKey() + ", value=" + notification.getValue();
        };

        // When
        guavaCacheService = new GuavaCacheService<>(1, 5, listener);
        guavaCacheService.put(key, value);
        guavaCacheService.put(key2, value2);
        guavaCacheService.getCache().asMap().forEach((k, v) -> System.out.println(k + " => " + v));
        System.out.println("actual => " + actual[0]);
        // Then
        assertTrue(actual[0].contains(key));
        assertTrue(actual[0].contains(value.toString()));
    }

    @Test
    void shouldReturnEvictionCount() throws InterruptedException {
        // Given
        String key1 = "one";
        Integer value1 = 1;
        String key2 = "two";
        Integer value2 = 2;

        // When
        guavaCacheService.put(key1, value1);
        guavaCacheService.put(key2, value2);
        guavaCacheService.get(key1);// access a key to trigger eviction

        long evictions = guavaCacheService.getEvictions();

        // Then
        assertEquals(0, evictions);
    }

    @Test
    void shouldReturnAveragePutTime() {
        // Given
        String key1 = "one";
        Integer value1 = 1;
        String key2 = "two";
        Integer value2 = 2;

        // When
        guavaCacheService.put(key1, value1);
        guavaCacheService.put(key2, value2);
        double avgPutTime = guavaCacheService.getAveragePutTime();

        // Then
        assertTrue(avgPutTime == 0);
    }
}