package org.app.cloud.service;

import static org.app.service.api.Service.PAYABLE_AGE;
import static org.app.service.api.Service.isPayableUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.app.cloud.service.exceptions.SubscriptionNotFoundException;
import org.app.model.BankCard;
import org.app.model.Subscription;
import org.app.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AppServiceTest {

    public static final User USER = new User("Vin", "Moon", LocalDate.now());
    private static AppService appService;

    @BeforeAll
    public static void setUp() {
        appService = new AppService();
    }

    @Test
    public void testSubscribeShouldAddSubscriptionIfNotExists() {
        var bankCard = new BankCard("1234", USER);
        appService.subscribe(bankCard);
        Optional<Subscription> subscription = appService.getSubscriptionByBankCardNumber("1234");
        assertTrue(subscription.isPresent());
    }

    @Test
    public void testSubscribeShouldNotAddSubscriptionIfExists() {
        var bankCard = new BankCard("5678", USER);
        BankDB.getSubscriptions().add(new Subscription("5678", LocalDate.now()));
        appService.subscribe(bankCard);
        long count = BankDB.getSubscriptions().stream().filter(s -> s.getBankcard().equals("5678")).count();
        assertEquals(1, count);
    }

    @Test
    public void testGetSubscriptionByBankCardNumberShouldThrowExceptionIfNotFound() {
        assertThrows(SubscriptionNotFoundException.class, ()-> {
            appService.getSubscriptionByBankCardNumber("9999");
        });
    }

    @Test
    public void testGetSubscriptionByBankCardNumberShouldReturnSubscriptionIfFound() {
        BankDB.getSubscriptions().add(new Subscription("1111", LocalDate.now()));
        Optional<Subscription> subscription = appService.getSubscriptionByBankCardNumber("1111");
        assertTrue(subscription.isPresent());
    }

    @Test
    public void testGetAllUsersShouldReturnAllUsers() {
        var appService = new AppService();
        var allUsers = appService.getAllUsers();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }

    @Test
    void testIsPayableUser(){
        User youngUser = new User("John", "Doe", LocalDate.now().minusYears(17));
        assertFalse(isPayableUser(youngUser));
        
        User payableUser = new User("Jane", "Doe", LocalDate.now().minusYears(PAYABLE_AGE));
        assertTrue(isPayableUser(payableUser));
        
        User olderUser = new User("Bob", "Smith", LocalDate.now().minusYears(30));
        assertTrue(isPayableUser(olderUser));
    }

    @Test
    public void testGetAllSubscriptionsByCondition() {
        List<Subscription> subscriptions = new ArrayList<>();
        var now = LocalDate.now();
        Subscription subscription1 = new Subscription("1234567890", now);
        Subscription subscription2 = new Subscription("0987654321", now);
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);

        BankDB.addSubscription(subscription1);
        BankDB.addSubscription(subscription2);
        
        Predicate<Subscription> subscriptionsCurrentYear = subscription -> subscription.getStartDate().getYear() == now.getYear();
        
        List<Subscription> result = appService.getAllSubscriptionsByCondition(subscriptionsCurrentYear);
        
        assertEquals(subscriptions, result);
    }
}