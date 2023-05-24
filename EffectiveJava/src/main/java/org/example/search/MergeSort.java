package org.example.search;

import java.util.Arrays;

public class MergeSort {
    public static void mergeSort(int[] arr) {
        if (arr.length > 1) {
            // Divide the array into two halves
            int mid = arr.length / 2;
            int[] left = Arrays.copyOfRange(arr, 0, mid);
            int[] right = Arrays.copyOfRange(arr, mid, arr.length);

            // Recursively sort each half
            mergeSort(left);
            mergeSort(right);

            // Merge the sorted halves
            int i = 0, j = 0, k = 0;
            while (i < left.length && j < right.length) {
                if (left[i] < right[j]) {
                    arr[k++] = left[i++];
                } else {
                    arr[k++] = right[j++];
                }
            }
            while (i < left.length) {
                arr[k++] = left[i++];
            }
            while (j < right.length) {
                arr[k++] = right[j++];
            }
        }
    }
}
