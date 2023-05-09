package org.app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.app.model.User;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testSetName() {
        User user = new User();
        user.setName("John");
        assertEquals("John", user.getName());
    }

    @Test
    void testSetSurname() {
        User user = new User();
        user.setSurname("Doe");
        assertEquals("Doe", user.getSurname());
    }

    @Test
    void testSetBirthday() {
        User user = new User();
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertEquals(LocalDate.of(2000, 1, 1), user.getBirthday());
    }
}