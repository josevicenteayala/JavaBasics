package com.epam.ld.module2.testing.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
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
        List<String> expectedVariables = new ArrayList<>();
        expectedVariables.add("name");
        expectedVariables.add("age");
        List<String> actualVariables = template.getVariableNames();
        assertEquals(expectedVariables, actualVariables);
    }

    @Test
    public void testGetVariableNamesWithDots() {
        Template template = new Template("Hello, #{user.name}! Your balance is $#{account.balance}.");
        List<String> expectedVariables = new ArrayList<>();
        expectedVariables.add("user.name");
        expectedVariables.add("account.balance");
        List<String> actualVariables = template.getVariableNames();
        assertEquals(expectedVariables, actualVariables);
    }
}