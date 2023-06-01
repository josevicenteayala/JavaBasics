package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.exceptions.MissingValueException;
import com.epam.ld.module2.testing.exceptions.PlaceholderValueException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        Set<String> variableNames = template.getVariableNames();
        Set<String> providedVariables = variables.keySet();

        Set<String> missingVariables = new HashSet<>(variableNames);
        missingVariables.removeAll(providedVariables);

        if (!missingVariables.isEmpty()) {
            throw new PlaceholderValueException("Missing variables detected: unable to replace all placeholders. Missing variables: " + missingVariables);
        }

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
