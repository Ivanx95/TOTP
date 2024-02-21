package com.example.cli.configuration;

import com.example.cli.CliContext;
import com.example.cli.configuration.exception.CLIBootstrapException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CLIDeamon {


    private static boolean running;


    @Autowired
    private FileWatcher watcher;

    private final String processName = "java.exe";

    private final String command = "WMIC path win32_process get Caption,Processid,Commandline";

    @PostConstruct
    public void postConstruct() {
        findProcess();

    }

    private Process findProcess() {


        try {
            return Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new CLIBootstrapException("Cannot check if deamon is already running, can lead to multiple demaon spanning", e);
        }

    }

    private boolean isProcessRunning() {
        Process p = findProcess();

        String pid = new ApplicationPid().toString();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((p.getInputStream())));) {

            List<String> processes = new ArrayList<>();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                processes.add(line);
            }
            return processes.stream().filter(row -> row.contains("-DappName=cli") && row.contains(processName) &&  !row.contains(pid))
                    .peek(LOGGER::info)
                    .findAny().map(i-> Boolean.TRUE)
                    .orElse(Boolean.FALSE);

        } catch (IOException ex) {
            throw new CLIBootstrapException("Cannot check if deamon is already running, can lead to multiple demaon spanning", ex);
        }


    }


    protected static Logger LOGGER = LoggerFactory.getLogger(CLIDeamon.class);

    public void start() {

        if (running || isProcessRunning()) {
            LOGGER.warn("Daemon already running in same VM");
            return;
        }


        try {
            watcher.watch();
        } catch (InterruptedException e) {
            //PLAN B
            //Cannot watch file in default location
            //Spin up web server?


        }
    }
}
