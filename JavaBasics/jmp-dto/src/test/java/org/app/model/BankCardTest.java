package org.app.model;

import org.app.model.BankCard;
import org.app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BankCardTest {
    @Test
    public void testGetNumber() {
        BankCard card = new BankCard("123", new User());
        Assertions.assertEquals("123", card.getNumber());
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setName("John Doe");
        BankCard card = new BankCard("123",user);
        Assertions.assertEquals(user, card.getUser());
    }
}