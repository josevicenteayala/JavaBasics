package org.example.search;

import java.util.Arrays;

public class BinarySearchWithMergeSort {

    public static <T extends Comparable<? super T>> int binarySearch(T[] a, T key) {
        int index = Arrays.binarySearch(a, key);
        return (index < 0) ? -1 : index;
    }

    public static <T extends Comparable<? super T>> void mergeSort(T[] a) {
        Arrays.sort(a);
    }

}
