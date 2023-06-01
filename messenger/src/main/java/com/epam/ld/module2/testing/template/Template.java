package com.epam.ld.module2.testing.template;

import java.util.ArrayList;
import java.util.List;
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

    public List<String> getVariableNames() {
        List<String> variableNames = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\{([\\w\\.]+)}");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            variableNames.add(matcher.group(1));
        }

        return variableNames;
    }
}
