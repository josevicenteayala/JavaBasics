package org.app.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.app.model.BankCard;
import org.app.model.DebitBankCard;
import org.junit.jupiter.api.Test;

class DebitBankCardTest {

    @Test
    public void testCreateDebitBankCard() {
        DebitBankCard debitBankCard = new DebitBankCard("123", new User());
        assertNotNull(debitBankCard);
    }

    @Test
    public void testDebitBankCardExtendsBankCard() {
        DebitBankCard debitCard = new DebitBankCard("123", new User());
        assertTrue(debitCard instanceof BankCard);
    }
}