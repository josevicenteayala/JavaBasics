package org.example.insertion;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class InsertionSortBenchmark {
    private int[] arr = { 12, 11, 13, 5, 6 };

    @Benchmark
    public void insertionSort() {
        InsertionSort.insertionSort(arr);
    }
}
