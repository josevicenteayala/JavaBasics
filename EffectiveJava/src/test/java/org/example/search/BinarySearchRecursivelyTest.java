package org.example.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BinarySearchRecursivelyTest {
    @Test
    public void testBinarySearchRecursive() {
        int[] arr = {1, 5, 10, 12, 17};
        assertEquals( 0, BinarySearchRecursively.binarySearchRecursive(arr, 1, 0, arr.length - 1));
        assertEquals(2, BinarySearchRecursively.binarySearchRecursive(arr, 10, 0, arr.length - 1));
        assertEquals( 4, BinarySearchRecursively.binarySearchRecursive(arr, 17, 0, arr.length - 1));
        assertEquals( -1, BinarySearchRecursively.binarySearchRecursive(arr, 6, 0, arr.length - 1));
    }
}