package com.epam.ld.module2.testing.modes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ConsoleModeTest {

    @Test
    void testPrintMessageUsingConsoleMode() {
        PrintOutput console = new ConsoleMode();
        String input = "Hello #{name}!";
        Map<String, String> variables = Map.of("name", "John");

        console.input(input).ctrlD();
        console.input(variables).ctrlP();

        assertEquals("Hello John!", console.getOutput());
    }

    @Test
    void testConsoleWithoutCtrlDCommand() {
        PrintOutput console = new ConsoleMode();
        String input = "Hello #{name}!";
        Map<String, String> variables = Map.of("name", "John");
        console.input(input);
        PrintOutput printOutput = console.input(variables);
        assertThrows(NullPointerException.class,()-> printOutput.ctrlP());
    }

    @Test
    void testConsoleWithoutCtrlPCommand() {
        PrintOutput console = new ConsoleMode();
        String input = "Hello #{name}!";
        Map<String, String> variables = Map.of("name", "John");

        console.input(input).ctrlD();
        console.input(variables);

        assertNull(console.getOutput());
    }
}