package org.app.cloud.service;

import org.app.model.Subscription;
import org.app.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankDB {

    private static final List<User> users = new ArrayList<>();
    private static final List<Subscription> subscriptions;

    static {
        var now = LocalDate.now();
        users.add(new User("Marc","Fix", LocalDate.of(now.getYear() - 48, 7, 24)));
        users.add(new User("Peter","Breath", LocalDate.of(now.getYear() - 47, 8, 25)));
        users.add(new User("Maria","De Gracia", LocalDate.of(now.getYear() - 46, 9, 26)));
        users.add(new User("Carol","Gi", LocalDate.of(now.getYear() - 45, 10, 27)));
        users.add(new User("August","Paris", LocalDate.of(now.getYear() - 44, 11, 28)));
        users.add(new User("George","Lukas", LocalDate.of(now.getYear() - 43, 12, 29)));
        users.add(new User("Santiago","Martir", LocalDate.of(now.getYear() - 42, 1, 30)));
        users.add(new User("Raquel","Wells", LocalDate.of(now.getYear() - 41, 2, 1)));
        users.add(new User("Sara","Lince", LocalDate.of(now.getYear() - 40, 3, 2)));
        users.add(new User("Veronica","Abad", LocalDate.of(now.getYear() - 39, 4, 3)));

        subscriptions = new ArrayList<>();
    }

    public static List<User> getUsers() {
        return users.stream().collect(Collectors.toUnmodifiableList());
    }

    public static void setUsers(List<User> usersList){
        users.clear();
        usersList.stream().forEach(u -> users.add(u));
    }

    public static List<Subscription> getSubscriptions() {
        return subscriptions;
    }


    public static boolean addSubscription(Subscription subscription){
        return getSubscriptions().add(subscription);
    }
}
