package com.epam.ld.module2.testing.modes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ConsoleModeTest {

    @Test
    public void testPrintMessageUsingConsoleMode() {
        PrintOutput console = new ConsoleMode();
        String input = "Hello #{name}!";
        Map<String, String> variables = Map.of("name", "John");

        console.input(input).ctrlD();
        console.input(variables).ctrlP();

        assertEquals("Hello John!", console.getOutput());
    }
}