package org.example.cache.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CacheServiceTest {
    private CacheService<String, String> cacheService;

    @BeforeEach
    public void setup() {
        long maxDurationInMillis = TimeUnit.MINUTES.toMillis(1);
        int maxSize = 10;
        EvictionPolicy policy = EvictionPolicy.LRU;
        RemovalListener<String, String> removalListener = notification -> { };
        cacheService = new CacheService<>(maxDurationInMillis, maxSize, policy, removalListener);
    }

    @Test
    public void getNonExistentKeyReturnsNull() {
        assertNull(cacheService.get("non-existent-key"));
    }

    @Test
    public void putAndGetWorks() {
        String key = "my-key";
        String value = "my-value";
        cacheService.put(key, value);
        assertEquals(value, cacheService.get(key));
    }

    @Test
    public void putOverridesPreviousValue() {
        String key = "my-key";
        String firstValue = "first-value";
        String secondValue = "second-value";
        cacheService.put(key, firstValue);
        assertEquals(firstValue, cacheService.get(key));
        cacheService.put(key, secondValue);
        assertEquals(secondValue, cacheService.get(key));
    }

    @Test
    public void evictsLeastRecentlyUsedEntryWhenMaxSizeReached() {
        for (int i = 0; i < 10; i++) {
            cacheService.put(String.valueOf(i), String.valueOf(i));
        }
        // Access some items so they are more recently used
        assertNotNull(cacheService.get("5"));
        assertNotNull(cacheService.get("6"));

        // Add a new item, causing eviction of the least recently used item
        String key = "new-key";
        String value = "new-value";
        cacheService.put(key, value);

        // Check that the least recently used item has been evicted
        assertNull(cacheService.get("0"));
    }

    @Test
    public void evictsLeastFrequentlyUsedEntryWhenMaxSizeReachedAndPolicyIsLFU() {
        cacheService = new CacheService<>(TimeUnit.MINUTES.toMillis(1), 3, EvictionPolicy.LFU, notification -> { });
        String key1 = "key-1";
        String key2 = "key-2";
        String key3 = "key-3";
        cacheService.put(key1, "value-1");
        cacheService.put(key2, "value-2");
        cacheService.put(key2, "value-2"); // One access to key2
        cacheService.put(key3, "value-3");
        cacheService.put(key3, "value-3"); // Two accesses to key3

        // Add a new item, causing eviction of the least frequently used item
        String key4 = "key-4";
        String value4 = "value-4";
        cacheService.put(key4, value4);

        // Ensure eviction of key1, since it had only one access
        assertNull(cacheService.get(key1));

        // Ensure key2 and key3 are still in cache since they were accessed more frequently
        assertEquals("value-2", cacheService.get(key2));
        assertEquals("value-3", cacheService.get(key3));

        // Access key3 again, making it the most frequently used
        cacheService.get(key3);

        // Add a new item, causing eviction of the least frequently used item
        String key5 = "key-5";
        String value5 = "value-5";
        cacheService.put(key5, value5);

        // Ensure eviction of key2, since it had fewer accesses than key3
        assertNull(cacheService.get(key2));

        // Ensure key3 and key4 are still in cache since they were accessed more frequently
        assertEquals("value-3", cacheService.get(key3));
        assertEquals("value-4", cacheService.get(key4));
    }

    @Test
    void testPutAndGet() {
        cacheService.put("key1", "value1");
        assertEquals("value1", cacheService.get("key1"));
    }

    @Test
    void testPutOverCapacity() {
        cacheService.put("key1", "value1");
        cacheService.put("key2", "value2");
        cacheService.put("key3", "value3");
        cacheService.put("key4", "value4");
        cacheService.put("key5", "value5");
        cacheService.put("key6", "value6");
        cacheService.put("key7", "value7");
        cacheService.put("key8", "value8");
        cacheService.put("key9", "value9");
        cacheService.put("key10", "value10");
        cacheService.put("key11", "value11");
        assertNull(cacheService.get("key1"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2})
    void testEvictionListener(int policyValue) {
        //Given
        RemovalNotification<String, String>[] removed = new RemovalNotification[1];
        EvictionPolicy policy = (policyValue == EvictionPolicy.LFU.getValue()) ? EvictionPolicy.LFU : EvictionPolicy.LRU;
        cacheService = new CacheService<>(100, 1, policy, notification -> {
            if (notification.getCause() == RemovalCause.SIZE) {
                removed[0] = notification;
            }
        });
        String key1 = "key1";
        String key2 = "key2";
        String value1 = "value1";
        String value2 = "value2";
        cacheService.put(key1, value1);
        //When
        cacheService.put(key2, value2);

        //Then
        assertNotNull(removed[0]);
        assertEquals(key1, removed[0].getKey());
        assertEquals(value1, removed[0].getValue());
    }

    @Test
    void testAveragePutTime() {
        cacheService = new CacheService<>(10000, 10, EvictionPolicy.LRU, notification -> {});
        for (int i = 0; i < 100; i++) {
            cacheService.put("key" + i, "value" + i);
        }
        double averagePutTime = cacheService.getAveragePutTime();
        assertTrue(averagePutTime < 1000000);
        System.out.println("Average put time (ns): " + averagePutTime);
    }

    @Test
    void testEvictExpiredEntries() {
        cacheService = new CacheService<>(100, 1, EvictionPolicy.LRU, notification -> {});
        cacheService.put("key1", "value1");
        cacheService.put("key2", "value1");
        assertNull(cacheService.get("key1"));
    }

    @Test
    void testLFUPolicy() {
        cacheService = new CacheService<>(1, 2, EvictionPolicy.LFU, notification -> {});
        cacheService.put("key1", "value1");
        cacheService.put("key2", "value2");
        cacheService.put("key3", "value3");
        cacheService.get("key1");
        cacheService.get("key1");
        cacheService.get("key2");
        cacheService.get("key2");
        cacheService.get("key2");
        cacheService.get("key3");
        cacheService.get("key3");
        cacheService.get("key3");
        cacheService.get("key3");
        String key1 = cacheService.get("key1");
        assertNull(key1);
        String key2 = cacheService.get("key2");
        assertNotNull(key2);
        String key3 = cacheService.get("key3");
        assertNotNull(key3);
    }
}