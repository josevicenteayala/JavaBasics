package com.epam.ld.module2.testing.modes;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

class FileOutputModeTest {

    public static final String HELLO_NAME = "Hello #{name}!";

    @Test
    @ExtendWith(TemporaryDirectoryExtension.class)
    public void testPrintMessageUsingFileModeWithTemporaryDirectory(Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("input.txt");
        Files.write(testFile, asList(HELLO_NAME));
        List<String> actualLines = Files.readAllLines(testFile);
        assertIterableEquals(asList(HELLO_NAME), actualLines);

        PrintOutput fileOutput = new FileOutputMode();

        fileOutput.input(testFile.toString()).ctrlD();
        Map<String, String> variables = Map.of("name", "John");
        fileOutput.input(variables).ctrlP();

        String expectedOutput = "Hello John!\n";
        assertEquals(expectedOutput, fileOutput.getOutput());
    }

    @Test
    public void testPrintMessageUsingFileModeAndSpyingFileBehavior() {
        String expectedOutput = "Hello John!\n";
        PrintOutput fileOutput = spy(FileOutputMode.class);
        doNothing().when(fileOutput).ctrlP();
        when(fileOutput.getOutput()).thenReturn(expectedOutput);

        fileOutput.input("input.txt").ctrlD();
        Map<String, String> variables = Map.of("name", "John");
        fileOutput.input(variables).ctrlP();

        assertEquals(expectedOutput, fileOutput.getOutput());
    }

    @Test
    public void testPrintMessageUsingFileModeAndMockingFileBehavior() {
        String expectedOutput = "Hello John!\n";
        PrintOutput fileOutput = mock(FileOutputMode.class);
        doNothing().when(fileOutput).ctrlP();
        doNothing().when(fileOutput).ctrlD();
        when(fileOutput.input(anyString())).thenReturn(fileOutput);
        when(fileOutput.input(any(Map.class))).thenReturn(fileOutput);
        when(fileOutput.getOutput()).thenReturn(expectedOutput);

        fileOutput.input("input.txt").ctrlD();
        Map<String, String> variables = Map.of("name", "John");
        fileOutput.input(variables).ctrlP();

        assertEquals(expectedOutput, fileOutput.getOutput());
    }
}