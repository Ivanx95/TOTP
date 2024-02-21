package com.example.cli.configuration.exception;

import java.io.IOException;

public class CLIBootstrapException extends RuntimeException{

    public CLIBootstrapException(String s, IOException e) {
        super(s,e);
    }
}
