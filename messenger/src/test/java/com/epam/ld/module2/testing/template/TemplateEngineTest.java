package com.epam.ld.module2.testing.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.ld.module2.testing.exceptions.MissingValueException;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class TemplateEngineTest {

    @Test
    public void testGenerateValidMessage() throws MissingValueException {
        TemplateEngine templateEngine = new TemplateEngine();
        Template template = new Template("Hello, #{name}! You are a #{language} programmer.");
        Map<String, String> variables = Map.of("name","Jose", "language","Java") ;
        String message = templateEngine.generateMessage(template, variables);
        assertEquals("Hello, Jose! You are a Java programmer.", message);
    }

    @Test
    public void testGenerateMessageWithIncompleteVariablesReplacements() {
        TemplateEngine templateEngine = new TemplateEngine();
        Template template = new Template("Hello, #{name}! You are a #{language} programmer at #{company}.");
        Map<String, String> variables = Map.of("name","Jose", "language","Java") ;
        assertThrows(MissingValueException.class, () -> templateEngine.generateMessage(template, variables));
    }

}
