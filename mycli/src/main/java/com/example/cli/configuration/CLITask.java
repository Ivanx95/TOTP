package com.example.cli.configuration;

import java.util.function.Consumer;

public abstract class CLITask<T> implements Consumer<T> {

    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
