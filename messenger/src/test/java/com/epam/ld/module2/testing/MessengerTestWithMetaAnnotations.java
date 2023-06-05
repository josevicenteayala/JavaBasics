package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import com.epam.metadata.UnitTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MessengerTestWithMetaAnnotations {

    public static final String MAIL_ADDRESS = "test@example.com";
    @Mock
    private static MailServer mailServer;
    private static Messenger messenger;

    @BeforeAll
    public static void init(){
        mailServer = mock(MailServer.class);
        TemplateEngine engine = new TemplateEngine();
        messenger = new Messenger(mailServer, engine);
    }

    @UnitTest
    @DisplayName("Test messenger using custom composed test")
    void testSendMessage() {
        Client client = new Client();
        client.setAddresses(MAIL_ADDRESS);
        Template template = new Template("Hello #{name}!");
        Map<String, String> variables = Map.of("name", "John");

        messenger.sendMessage(client, template, variables);

        verify(mailServer).send(MAIL_ADDRESS, "Hello John!");
    }

}
