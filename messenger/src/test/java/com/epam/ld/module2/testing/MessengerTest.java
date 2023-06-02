package com.epam.ld.module2.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ld.module2.testing.modes.ConsoleMode;
import com.epam.ld.module2.testing.modes.FileOutputMode;
import com.epam.ld.module2.testing.modes.PrintOutput;
import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class MessengerTest {

    @Test
    public void testSendMessage() {
        MailServer mailServer = mock(MailServer.class);
        TemplateEngine engine = new TemplateEngine();
        Messenger messenger = new Messenger(mailServer, engine);
        Client client = new Client();
        client.setAddresses("test@example.com");
        Template template = new Template("Hello #{name}!");
        Map<String, String> variables = Map.of("name", "John");

        messenger.sendMessage(client, template, variables);

        verify(mailServer).send(eq("test@example.com"), eq("Hello John!"));
    }

    @Test
    public void testPrintMessageUsingConsoleMode() {
        PrintOutput console = new ConsoleMode();
        TemplateEngine engine = new TemplateEngine();
        MailServer mailServer = mock(MailServer.class);
        Messenger messenger = new Messenger(mailServer, engine);
        String input = "Hello #{name}!";
        Map<String, String> variables = Map.of("name", "John");

        console.input(input).ctrlD();
        console.input(variables).ctrlP();

        assertEquals("Hello John!", console.getOutput());
    }

    /**
     * This test is marked as disable because it requires a real input file and produce a new file with results.
     * Authorizing input/output routes could work.
     * */
    @Test
    @Disabled
    public void testPrintMessageUsingFileMode() {
        PrintOutput fileOutput = new FileOutputMode();
        MailServer mailServer = mock(MailServer.class);
        doNothing().when(mailServer).send(any(String.class), any(String.class));

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