package com.epam.ld.module2.testing.modes;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class FileOutputMode implements PrintOutput {

    private Template template;
    private TemplateEngine templateEngine;
    private String inputFileName;
    private Map<String, String> variables;
    private Path pathFile;
    private String output;

    @Override
    public PrintOutput input(String inputFileName) {
        this.inputFileName = inputFileName;
        return this;
    }

    @Override
    public PrintOutput input(Map<String, String> variables) {
        this.variables = variables;
        return this;
    }

    @Override
    public void ctrlD() {
        pathFile = Path.of(inputFileName);
    }

    @Override
    public void ctrlP() {
        try {
            String text = Files.readString(pathFile);
            template = new Template(text);
            templateEngine = new TemplateEngine();
            output = templateEngine.generateMessage(template, variables);
            Files.writeString(Path.of("/home/joseayala/output.txt"), output, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getOutput() {
        return output;
    }
}
