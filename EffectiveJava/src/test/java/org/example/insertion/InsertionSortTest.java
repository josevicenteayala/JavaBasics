package org.example.insertion;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class InsertionSortTest {
    @Test
    public void testInsertionSort() {
        int[] arr = { 12, 11, 13, 5, 6 };
        int[] expectedArr = { 5, 6, 11, 12, 13 };
        InsertionSort.insertionSort(arr);
        assertArrayEquals(expectedArr, arr);
    }

    @Test
    public void testInsertionSortWithDuplicateValues() {
        int[] arr = { 10, 7, 8, 9, 1, 5, 5 };
        int[] expectedArr = { 1, 5, 5, 7, 8, 9, 10 };
        InsertionSort.insertionSort(arr);
        assertArrayEquals(expectedArr, arr);
    }

    @Test
    public void testInsertionSortWithEmptyArray() {
        int[] arr = {};
        int[] expectedArr = {};
        InsertionSort.insertionSort(arr);
        assertArrayEquals(expectedArr, arr);
    }
}