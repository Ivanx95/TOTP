package com.example.cli;

import com.example.cli.configuration.CLIDeamon;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CliApplication implements CommandLineRunner {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CliApplication.class);

    @Autowired
    private CLIDeamon cLiDeamon;
    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Running command");
        cLiDeamon.start();
    }
}
