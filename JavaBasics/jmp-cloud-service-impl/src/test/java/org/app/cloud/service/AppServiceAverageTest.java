package org.app.cloud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import org.app.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppServiceAverageTest {
    private static AppService appService;

    @BeforeAll
    public static void setUp() {
        appService = new AppService();
    }

    @Test
    public void testGetAverageUserAgeCustomUsers() {
        var now = LocalDate.now();
        User user1 = new User("Alice","Hilton", LocalDate.of(now.getYear()-28, 9, 3));
        User user2 = new User("Bob","Moon", LocalDate.of(now.getYear()-23, 1, 1));
        User user3 = new User("Charlie","Sun", LocalDate.of(now.getYear()-48, 12, 31));
        User user4 = new User("Dave","Mars", LocalDate.of(now.getYear()-53, 6, 15));
        User user5 = new User("Eve","Jupiter", LocalDate.of(now.getYear()-24, 3, 18));

        var users = List.of(user1, user2, user3, user4, user5);
        BankDB.setUsers(users);
        var appService = new AppService();
        var averageUsersAge = appService.getAverageUsersAge();
        assertEquals(34.6,averageUsersAge);
    }

    @Test
    public void testGetAverageUserAgeEmptyUsers() {
        BankDB.setUsers(List.of());
        var appService = new AppService();
        var averageUsersAge = appService.getAverageUsersAge();
        assertEquals(0,averageUsersAge);
    }

    @Test
    public void testGetAverageUserAgeWithOneUserOnly() {
        var now = LocalDate.now();
        User user1 = new User("Alice","Hilton", LocalDate.of(now.getYear()-28, 9, 3));

        var users = List.of(user1);
        BankDB.setUsers(users);
        var appService = new AppService();
        var averageUsersAge = appService.getAverageUsersAge();
        assertEquals(27,averageUsersAge);
    }

    @Test
    public void testGetAverageUserAgeWithTwoUsersWithSameAge() {
        var now = LocalDate.now();
        User user1 = new User("Alice","Hilton", LocalDate.of(now.getYear()-28, 9, 3));
        User user2 = new User("Marie","Hilton", LocalDate.of(now.getYear()-28, 9, 3));

        var users = List.of(user1, user2);
        BankDB.setUsers(users);
        var appService = new AppService();
        var averageUsersAge = appService.getAverageUsersAge();
        assertEquals(27,averageUsersAge);
    }
}
