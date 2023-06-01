package com.epam.ld.module2.testing.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class TemplateTest {

    @Test
    public void testConstructor() {
        Template template = new Template("Hello, world!");
        assertEquals(Template.class,template.getClass());
    }

    @Test
    public void testValidateConstructorInjection() {
        Template template = new Template("Hello, world!");
        assertEquals("Hello, world!", template.getText());
    }

    @Test
    public void testGetVariableNames() {
        Template template = new Template("Hello, #{name}! You are #{age} years old.");
        Set<String> expectedVariables = new HashSet<>();
        expectedVariables.add("name");
        expectedVariables.add("age");
        Set<String> actualVariables = template.getVariableNames();
        assertEquals(expectedVariables, actualVariables);
    }

    @Test
    public void testGetVariableNamesWithDots() {
        Template template = new Template("Hello, #{user.name}! Your balance is $#{account.balance}.");
        Set<String> expectedVariables = new HashSet<>();
        expectedVariables.add("user.name");
        expectedVariables.add("account.balance");
        Set<String> actualVariables = template.getVariableNames();
        assertEquals(expectedVariables, actualVariables);
    }

    @Test
    public void testGetVariableNamesWithDuplicates() {
        Template template = new Template("Hello, #{name}! Your age is #{age}. Hello again, #{name}!");
        Set<String> expectedVariables = new HashSet<>();
        expectedVariables.add("name");
        expectedVariables.add("age");
        Set<String> actualVariables = template.getVariableNames();
        assertEquals(expectedVariables, actualVariables);
    }
}