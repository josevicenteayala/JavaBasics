package com.epam.ld.module2.testing.modes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.epam.ld.module2.testing.MailServer;
import com.epam.ld.module2.testing.Messenger;
import com.epam.ld.module2.testing.template.TemplateEngine;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ConsoleModeTest {

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
}