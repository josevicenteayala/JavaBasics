package com.epam.ld.module2.testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

public class MailServerTest {

    @Test
    public void testSendEmail(){
        MailServer mailServer = mock(MailServer.class);
        String mail = "mock@email.com";
        String messageContent = "Dear mock, hope you are doing well.";
        mailServer.send(mail, messageContent);

        verify(mailServer).send(mail, messageContent);
    }

}
