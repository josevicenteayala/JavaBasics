package org.app.cloud;

import java.util.UUID;
import org.app.Bank;
import org.app.model.BankCardType;
import org.app.model.CreditBankCard;
import org.app.model.DebitBankCard;
import org.app.model.User;
import org.app.model.BankCard;

public class SecondBank implements Bank {
    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        BankCard bankCard = null;
        switch (bankCardType) {
            case DEBIT -> bankCard = new DebitBankCard(UUID.randomUUID().toString(),user);
            case CREDIT -> bankCard = new CreditBankCard(UUID.randomUUID().toString(),user);
        }
        return bankCard;
    }
}
