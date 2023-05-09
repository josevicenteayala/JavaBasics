package org.app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.app.model.BankCardType;
import org.junit.jupiter.api.Test;

class BankCardTypeTest {
    @Test
    void testCreditEquals() {
        BankCardType cardType1 = BankCardType.CREDIT;
        BankCardType cardType2 = BankCardType.CREDIT;

        assertEquals(cardType1, cardType2);
    }

    @Test
    void testDebitNotEqualsCredit() {
        BankCardType cardType1 = BankCardType.DEBIT;
        BankCardType cardType2 = BankCardType.CREDIT;

        assertNotEquals(cardType1, cardType2);
    }

    @Test
    void testValueOfCredit() {
        BankCardType cardType = BankCardType.valueOf("CREDIT");

        assertEquals(BankCardType.CREDIT, cardType);
    }

    @Test
    void testValueOfInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            BankCardType.valueOf("INVALID_TYPE");
        });
    }
}