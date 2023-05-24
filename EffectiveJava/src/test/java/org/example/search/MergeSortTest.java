package org.example.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    @Test
    public void testEmptyArray() {
        int[] arr = {};
        MergeSort.mergeSort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    public void testSingleElementArray() {
        int[] arr = {5};
        MergeSort.mergeSort(arr);
        assertArrayEquals(new int[]{5}, arr);
    }

    @Test
    public void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        MergeSort.mergeSort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        MergeSort.mergeSort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testRandomArray() {
        int[] arr = {4, 2, 1, 6, 3, 5};
        MergeSort.mergeSort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, arr);
    }
}