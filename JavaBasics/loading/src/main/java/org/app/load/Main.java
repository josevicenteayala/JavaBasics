package org.app.load;

import java.time.LocalDate;
import java.util.ServiceLoader;
import org.app.model.BankCard;
import org.app.model.Subscription;
import org.app.model.User;
import org.app.service.api.Service;


public class Main {
    public static void main(String[] args) {
        serviceLoader();
    }

    private static void serviceLoader() {
        Service service = ServiceLoader.load(Service.class).findFirst().orElseThrow(IllegalStateException::new);
        System.out.println("Creating a user");
        var user = new User("Vin", "Moon", LocalDate.now());
        System.out.println("User has been created " + user);
        var bankCard = new BankCard("1234", user);
        System.out.println("BankCard has been created " + bankCard);
        var subscription = new Subscription("1234", LocalDate.now());
        System.out.println("Subscription has been created " + subscription);
        service.subscribe(bankCard);
        var subscriptionByBankCardNumber = service.getSubscriptionByBankCardNumber("1234");
        System.out.println("Subscription recovered 1234: " + subscriptionByBankCardNumber);
    }
}
