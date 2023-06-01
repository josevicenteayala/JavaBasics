package com.epam.ld.module2.testing.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}