package com.epam.ld.module2.testing.modes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class Parameterized {

    @TestFactory
    public Stream<DynamicTest> testMultiply() {
        Collection<Object[]> data = Arrays.asList(
                new Object[]{1, 2},
                new Object[]{3, 6},
                new Object[]{5, 10}
        );

        return data.stream()
                .map(input -> dynamicTest("Input: " + input[0], () -> {
                    SomeClass sc = new SomeClass();
                    int result = sc.multiplyByTwo((int) input[0]);
                    assertEquals((int) input[1], result);
                }));
    }

    private class SomeClass {
        public int multiplyByTwo(int i) {
            return 2 * i;
        }
    }
}
