package com.epam.ld.module2.testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MessengerTest {

    @Test
    void testSendMessage() {
        MailServer mailServer = mock(MailServer.class);
        TemplateEngine engine = new TemplateEngine();
        Messenger messenger = new Messenger(mailServer, engine);
        Client client = new Client();
        client.setAddresses("test@example.com");
        Template template = new Template("Hello #{name}!");
        Map<String, String> variables = Map.of("name", "John");

        messenger.sendMessage(client, template, variables);

        verify(mailServer).send("test@example.com", "Hello John!");
    }

}