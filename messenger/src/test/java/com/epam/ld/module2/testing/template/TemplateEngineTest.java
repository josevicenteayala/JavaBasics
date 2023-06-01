package com.epam.ld.module2.testing.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.Test;

public class TemplateEngineTest {

    @Test
    public void testGenerateValidMessage(){
        TemplateEngine templateEngine = new TemplateEngine();
        Template template = new Template("Hello, #{name}! You are a #{language} programmer.");
        String message = templateEngine.generateMessage(template, new Client());
        assertEquals("Hello, Jose! You are a Java programmer.", message);
    }

}
