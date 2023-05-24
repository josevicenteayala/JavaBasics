package org.example.search;

public class BinarySearchRecursively {
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        // base case (target not found)
        if (left > right) {
            return -1;
        }

        // find the middle index
        int mid = left + (right - left) / 2;

        // check if the target is at the middle index
        if (arr[mid] == target) {
            return mid;
        }
        // if target is smaller than the middle element
        // then it can only be present in left sub-array
        else if (arr[mid] > target) {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
        // else the target must be in right sub-array
        else {
            return binarySearchRecursive(arr, target, mid + 1, right);
        }
    }
}
