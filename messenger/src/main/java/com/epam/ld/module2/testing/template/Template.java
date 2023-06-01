package com.epam.ld.module2.testing.template;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Template.
 */
public class Template {

    private final String text;

    public Template(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Set<String> getVariableNames() {
        Set<String> variableNames = new HashSet<>();
        Pattern pattern = Pattern.compile("#\\{([\\w\\.]+)}");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            variableNames.add(matcher.group(1));
        }

        return variableNames;
    }
}
