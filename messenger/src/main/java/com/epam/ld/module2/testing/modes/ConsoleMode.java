package com.epam.ld.module2.testing.modes;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.util.Map;

public class ConsoleMode implements PrintOutput {
    private Template template;
    private TemplateEngine templateEngine;
    private String text;
    private Map<String, String> variables;
    private String output;

    public ConsoleMode(){
        templateEngine = new TemplateEngine();
    }

    @Override
    public PrintOutput input(String input) {
        text = input;
        return this;
    }

    @Override
    public PrintOutput input(Map<String, String> variables) {
        this.variables = variables;
        return this;
    }

    @Override
    public void ctrlD() {
        template = new Template(text);
    }

    @Override
    public void ctrlP() {
        output = templateEngine.generateMessage(template, variables);
    }

    @Override
    public String getOutput() {
        return output;
    }
}
