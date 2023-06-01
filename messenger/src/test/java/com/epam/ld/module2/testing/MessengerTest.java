package com.epam.ld.module2.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.epam.ld.module2.testing.modes.ConsoleMode;
import com.epam.ld.module2.testing.modes.PrintOutput;
import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import java.util.Map;
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
    public void testConsoleMode() {
        PrintOutput console = new ConsoleMode();
        TemplateEngine engine = new TemplateEngine();
        MailServer mailServer = mock(MailServer.class);
        Messenger messenger = new Messenger(mailServer, engine);
        String input = "Hello #{name}!";
        Map<String, String> variables = Map.of("name", "John");

        console.input(input).ctrlD();
        console.input(variables).ctrlP();
        messenger.printMode(console);

        assertEquals("Hello John!", console.getOutput());
    }

    @Test
    public void testFileMode(){
        PrintOutput fileOutput = new FileOutput();
        TemplateEngine engine = new TemplateEngine();
        MailServer mailServer = mock(MailServer.class);
        Messenger messenger = new Messenger(mailServer, engine);
        fileOutput.input("input.txt").ctrlD();
        Map<String, String> variables = Map.of("name", "John");
        fileOutput.input(variables).ctrlP();

        messenger.printMode(fileOutput);

        assertEquals("Hello John!", fileOutput.getOutput());
    }

}