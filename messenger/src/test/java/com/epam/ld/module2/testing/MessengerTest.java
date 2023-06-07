package com.epam.ld.module2.testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.mockito.Mock;

class MessengerTest {

    public static final String MAIL_ADDRESS = "test@example.com";
    @Mock
    private static MailServer mailServer;
    @Mock
    private static MailServer mailServerSpy;
    private static Messenger messenger;
    private static Messenger messengerSpy;

    @BeforeEach
    public void init(){
        mailServer = mock(MailServer.class);
        mailServerSpy = spy(MailServer.class);
        TemplateEngine engine = new TemplateEngine();
        messenger = new Messenger(mailServer, engine);
        messengerSpy = new Messenger(mailServerSpy,engine);
    }

    @Test
    void testSendMessage() {
        Client client = new Client();
        client.setAddresses(MAIL_ADDRESS);
        Template template = new Template("Hello #{name}!");
        Map<String, String> variables = Map.of("name", "John");

        messenger.sendMessage(client, template, variables);

        verify(mailServer).send(MAIL_ADDRESS, "Hello John!");
    }

    @TestFactory
    Stream<DynamicTest> testSendMessages() {
        Collection<Object[]> templates = Arrays.asList(
                new Object[]{
                        new Template("São Paulo: #{number} é uma cidade incrível!"),"São Paulo: 65 é uma cidade incrível!"},
                new Object[]{
                        new Template("Hello #{name}!"),"Hello John!"},
                new Object[]{
                        new Template("The interview for #{rol} will take place in Room #{room}."),"The interview for engineers will take place in Room 666."}
        );
        Map<String, String> variables = Map.of("name", "John",
                "number","65",
                "rol","engineers",
                "room","666");
        Client client = new Client();
        client.setAddresses(MAIL_ADDRESS);

        return templates.stream()
                .map(object -> {
                    Template template = (Template) object[0];
                    String expectedResult = object[1].toString();
                    return DynamicTest.dynamicTest("Template text: " + template.getText(),
                            ()-> {
                                messenger.sendMessage(client, template, variables);
                                verify(mailServer).send(MAIL_ADDRESS, expectedResult);
                            });
                });
    }

    @Test
    void testSendMessageUsingPartialMock() {
        Client client = new Client();
        client.setAddresses(MAIL_ADDRESS);
        Template template = new Template("Hello #{name}!");
        Map<String, String> variables = Map.of("name", "John");

        messengerSpy.sendMessage(client, template, variables);

        verify(mailServerSpy).send(MAIL_ADDRESS, "Hello John!");
    }

    @DisabledOnOs(OS.SOLARIS)
    @Test
    void testSendMessageOnlyWhenPropertyIsTrue() {
        Client client = new Client();
        client.setAddresses(MAIL_ADDRESS);
        Template template = new Template("Hello #{name}!");
        Map<String, String> variables = Map.of("name", "John");

        messengerSpy.sendMessage(client, template, variables);

        verify(mailServerSpy).send(MAIL_ADDRESS, "Hello John!");
    }

}