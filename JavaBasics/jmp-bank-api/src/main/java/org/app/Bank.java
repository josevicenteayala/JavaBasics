package org.app;

import org.app.model.BankCardType;
import org.app.model.BankCard;
import org.app.model.User;

@FunctionalInterface
public interface Bank {

    BankCard createBankCard(User user, BankCardType bankCardType);

}
