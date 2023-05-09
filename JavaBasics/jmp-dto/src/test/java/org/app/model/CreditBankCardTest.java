package org.app.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.app.model.BankCard;
import org.app.model.CreditBankCard;
import org.junit.jupiter.api.Test;

class CreditBankCardTest {

    @Test
    public void testCreateCreditBankCard() {
        CreditBankCard card = new CreditBankCard("123", new User());
        assertNotNull(card);
    }

    @Test
    public void testCreditBankCardIsInstanceOfBankCard() {
        CreditBankCard card = new CreditBankCard("123", new User());
        assertTrue(card instanceof BankCard);
    }

}