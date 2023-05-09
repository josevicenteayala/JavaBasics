package org.app.custom.service;

import org.app.model.BankCard;
import org.app.model.Subscription;
import org.app.model.User;
import org.app.service.api.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AppService implements Service {

    private final List<BankCard> bankCards;
    private final List<Subscription> subscriptions;

    public AppService() {
        bankCards = new ArrayList<>();
        subscriptions = new ArrayList<>();

        subscriptions.add(new Subscription("123456", LocalDate.now()));
        subscriptions.add(new Subscription("789456", LocalDate.now()));
    }
    @Override
    public void subscribe(BankCard bankCard) {
        bankCards.add(bankCard);
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return List.of(new User("Baby","Bummer", LocalDate.now()));
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return subscriptions;
    }
}
