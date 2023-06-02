package com.epam.ld.module2.testing.modes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.MailServer;
import com.epam.ld.module2.testing.Messenger;
import com.epam.ld.module2.testing.template.TemplateEngine;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
class FileOutputModeTest {

    /**
     * This test is marked as disable because it requires a real input file and produce a new file with results.
     * Authorizing input/output routes could work.
     * */
    @Test
    @Disabled
    public void testPrintMessageUsingFileMode() {
        PrintOutput fileOutput = new FileOutputMode();

        fileOutput.input("/home/joseayala/input.txt").ctrlD();
        Map<String, String> variables = Map.of("name", "John");
        fileOutput.input(variables).ctrlP();

        String expectedOutput = "Hello John!\n";
        assertEquals(expectedOutput, fileOutput.getOutput());

        //Read the file created with the response
        Path pathResponse = Path.of("/home/joseayala/output.txt");
        try {
            String output = Files.readString(pathResponse);
            assertEquals(expectedOutput, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPrintMessageUsingFileModeAndMockingFileBehavior() {
        String expectedOutput = "Hello John!\n";
        PrintOutput fileOutput = spy(FileOutputMode.class);
        doNothing().when(fileOutput).ctrlP();
        when(fileOutput.getOutput()).thenReturn(expectedOutput);

        TemplateEngine engine = new TemplateEngine();
        MailServer mailServer = mock(MailServer.class);
        doNothing().when(mailServer).send(any(String.class), any(String.class));
        Messenger messenger = new Messenger(mailServer, engine);
        Client client = new Client();
        client.setAddresses("test@example.com");

        fileOutput.input("/home/joseayala/input.txt").ctrlD();
        Map<String, String> variables = Map.of("name", "John");
        fileOutput.input(variables).ctrlP();

        assertEquals(expectedOutput, fileOutput.getOutput());

        //Read the file created with the response
        Path pathResponse = Path.of("/home/joseayala/output.txt");
        try {
            String output = Files.readString(pathResponse);
            assertEquals(expectedOutput, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}