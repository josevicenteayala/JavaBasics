package org.app.cloud;

import static org.junit.jupiter.api.Assertions.*;

import org.app.Bank;
import org.app.model.BankCardType;
import org.app.model.CreditBankCard;
import org.app.model.DebitBankCard;
import org.app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyBankTest {

    private Bank myBank;

    @BeforeEach
    void setUp() {
        myBank = MyBank::createBankCard;
    }

    @Test
    void createDebitBankCard() {
        var user = new User("John", "Doe");
        var debitCard = myBank.createBankCard(user, BankCardType.DEBIT);

        assertNotNull(debitCard);
        assertTrue(debitCard instanceof DebitBankCard);
        assertNotNull(debitCard.getNumber());
        assertEquals(user, debitCard.getUser());
    }

    @Test
    void createCreditBankCard() {
        var user = new User("Jane", "Doe");
        var creditCard = myBank.createBankCard(user, BankCardType.CREDIT);

        assertNotNull(creditCard);
        assertTrue(creditCard instanceof CreditBankCard);
        assertNotNull(creditCard.getNumber());
        assertEquals(user, creditCard.getUser());
    }
}