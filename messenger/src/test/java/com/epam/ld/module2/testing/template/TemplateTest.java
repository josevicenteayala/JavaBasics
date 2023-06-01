package com.epam.ld.module2.testing.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemplateTest {

    @Test
    public void testConstructor() {
        Template template = new Template("Hello, world!");
        assertEquals(Template.class,template.getClass());
    }

}