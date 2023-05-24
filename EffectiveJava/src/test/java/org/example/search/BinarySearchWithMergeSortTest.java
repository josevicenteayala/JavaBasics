package org.example.search;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BinarySearchWithMergeSortTest {
    @Test
    void testBinarySearch() {
        Integer[] a = {1, 3, 5, 7, 9};
        int index = BinarySearchWithMergeSort.binarySearch(a, 7);
        assertEquals(3, index);
    }

    @Test
    void testBinarySearchNotFound() {
        Integer[] a = {1, 3, 5, 7, 9};
        int index = BinarySearchWithMergeSort.binarySearch(a, 4);
        assertEquals(-1, index);
    }

    @Test
    void testMergeSort() {
        Integer[] a = {7, 3, 5, 1, 9};
        BinarySearchWithMergeSort.mergeSort(a);
        Integer[] expected = {1, 3, 5, 7, 9};
        assertArrayEquals(expected, a);
    }
}