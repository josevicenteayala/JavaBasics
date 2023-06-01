package com.epam.ld.module2.testing.template;

import java.util.List;

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
        return List.of();
    }
}
