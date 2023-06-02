package com.epam.ld.module2.testing;

/**
 * Mail server class.
 */
public class MailServer {

    /**
     * Send notification.
     *
     * @param addresses  the addresses
     * @param messageContent the message content
     */
    public void send(String addresses, String messageContent) {
        System.out.println(String.format("Sending message to %s with this content: %s",addresses, messageContent));
    }
}
