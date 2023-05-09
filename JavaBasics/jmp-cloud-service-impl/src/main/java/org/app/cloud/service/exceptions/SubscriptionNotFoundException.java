package org.app.cloud.service.exceptions;

public class SubscriptionNotFoundException extends RuntimeException {


    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
