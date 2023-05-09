package org.app.cloud.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.app.cloud.service.exceptions.SubscriptionNotFoundException;
import org.app.model.BankCard;
import org.app.model.Subscription;
import org.app.model.User;
import org.app.service.api.Service;

public class AppService implements Service {

    private static final Logger LOGGER = Logger.getLogger(AppService.class.getName());
    private static List<User> users;

    public AppService() {
        users = BankDB.getUsers();
    }

    @Override
    public void subscribe(BankCard bankCard) {
        try {
            getSubscriptionByBankCardNumber(bankCard.getNumber());
        } catch (SubscriptionNotFoundException exception) {
            BankDB.addSubscription(new Subscription(bankCard.getNumber(), LocalDate.now()));
            LOGGER.log(Level.FINER, exception.getMessage());
        }
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.ofNullable(BankDB.getSubscriptions().stream().filter(s -> s.getBankcard().equals(cardNumber))
                .findFirst()
                .orElseThrow(() -> new SubscriptionNotFoundException("No subscription found with card number: " + cardNumber)));
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return BankDB.getSubscriptions().stream().filter(predicate).collect(Collectors.toUnmodifiableList());
    }
}
