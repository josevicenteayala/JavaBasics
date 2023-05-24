package org.example.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BinarySearchTest {

    @Test
    public void testBinarySearch() {
        int[] arr1 = {1, 2, 3, 4, 5};
        assertEquals(BinarySearch.binarySearch(arr1, 1), 0);
        assertEquals(BinarySearch.binarySearch(arr1, 2), 1);
        assertEquals(BinarySearch.binarySearch(arr1, 3), 2);
        assertEquals(BinarySearch.binarySearch(arr1, 4), 3);
        assertEquals(BinarySearch.binarySearch(arr1, 5), 4);

        int[] arr2 = {2, 4, 6, 8, 10};
        assertEquals(BinarySearch.binarySearch(arr2, 2), 0);
        assertEquals(BinarySearch.binarySearch(arr2, 4), 1);
        assertEquals(BinarySearch.binarySearch(arr2, 6), 2);
        assertEquals(BinarySearch.binarySearch(arr2, 8), 3);
        assertEquals(BinarySearch.binarySearch(arr2, 10), 4);

        int[] arr3 = {-5, 0, 25, 30};
        assertEquals(BinarySearch.binarySearch(arr3, -5), 0);
        assertEquals(BinarySearch.binarySearch(arr3, 0), 1);
        assertEquals(BinarySearch.binarySearch(arr3, 25), 2);
        assertEquals(BinarySearch.binarySearch(arr3, 30), 3);

        int[] arr4 = {};
        assertEquals(BinarySearch.binarySearch(arr4, 5), -1);

        int[] arr5 = {1, 2, 3, 4, 5};
        assertEquals(BinarySearch.binarySearch(arr5, 6), -1);
    }

}