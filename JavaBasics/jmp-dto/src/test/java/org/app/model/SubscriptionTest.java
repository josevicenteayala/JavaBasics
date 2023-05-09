package org.app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.app.model.Subscription;
import org.junit.jupiter.api.Test;

class SubscriptionTest {
    @Test
    public void testSetAndGetBankcard() {
        Subscription sub = new Subscription();
        String bankcard = "1234567890123456";
        sub.setBankcard(bankcard);
        assertEquals(bankcard, sub.getBankcard());
    }

    @Test
    public void testSetAndGetStartDate() {
        Subscription sub = new Subscription();
        LocalDate startDate = LocalDate.now();
        sub.setStartDate(startDate);
        assertEquals(startDate, sub.getStartDate());
    }
}