package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.exceptions.MissingValueException;

import java.util.Map;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    /**
     * Generate message string.
     *
     * @param template the template
     * @param variables with the values to replace the placeholders
     * @return the string
     */
    public String generateMessage(Template template, Map<String, String> variables) throws MissingValueException {
        String text = template.getText();
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String key = "#{" + entry.getKey() + "}";
            if (text.contains(key)) {
                text = text.replace(key, entry.getValue());
            }
        }
        if (text.contains("#{")) {
            throw new MissingValueException("Failed to generate the message: incomplete placeholder replacement.");
        }
        return text;
    }
}
