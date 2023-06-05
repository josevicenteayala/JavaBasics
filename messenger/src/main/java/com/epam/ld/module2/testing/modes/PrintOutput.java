package com.epam.ld.module2.testing.modes;

import java.util.Map;

public interface PrintOutput {

    PrintOutput input(String input);

    PrintOutput input(Map<String, String> variables);

    void ctrlD();

    void ctrlP();

    String getOutput();
}

