package com.epam.ld.module2.testing.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epam.ld.module2.testing.exceptions.PlaceholderValueException;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class TemplateEngineTest {

    @Test
    public void testGenerateValidMessage() {
        TemplateEngine templateEngine = new TemplateEngine();
        Template template = new Template("Hello, #{name}! You are a #{language} programmer.");
        Map<String, String> variables = Map.of("name","Jose", "language","Java") ;
        String message = templateEngine.generateMessage(template, variables);
        assertEquals("Hello, Jose! You are a Java programmer.", message);
    }

    @Test
    public void testGenerateErrorWhenIncompleteVariablesAreProvided() {
        TemplateEngine templateEngine = new TemplateEngine();
        Template template = new Template("Hello, #{name}! You are a #{language} programmer at #{company}.");
        Map<String, String> variables = Map.of("name","Jose", "language","Java") ;
        assertThrows(PlaceholderValueException.class, () -> templateEngine.generateMessage(template, variables));
    }

    @Test
    public void testGenerateMessageWithAdditionalVariablesReplacements() {
        TemplateEngine templateEngine = new TemplateEngine();
        Template template = new Template("Hello, #{name}! You are a #{language} programmer.");
        Map<String, String> variables = Map.of("name","Jose", "language","Java", "tag","Not used");
        String message = templateEngine.generateMessage(template, variables);
        assertEquals("Hello, Jose! You are a Java programmer.", message);
    }

    @Test
    public void testGenerateMessageWithMissingVariablesReplacements() {
        TemplateEngine templateEngine = new TemplateEngine();
        Template template = new Template("Hello, #{name}! You are a #{language} programmer.");
        Map<String, String> variables = Map.of("name","Jose") ;
        assertThrows(PlaceholderValueException.class, () -> templateEngine.generateMessage(template, variables));
    }

    @Test
    public void testVariableReplacementWithAnotherTag() {
        TemplateEngine engine = new TemplateEngine();
        Template template = new Template("Hello #{name}!");
        Map<String, String> variables = Map.of("name", "#{lastName}");

        String output = engine.generateMessage(template, variables);

        assertEquals("Hello #{lastName}!", output);
    }

    @Test
    public void testVariableReplacementWithoutPlaceholders() {
        TemplateEngine templateEngine = new TemplateEngine();
        Template template = new Template("Hello team!");
        Map<String, String> variables = Map.of("name", "Jose");
        assertThrows(PlaceholderValueException.class, () -> templateEngine.generateMessage(template, variables));
    }
}
