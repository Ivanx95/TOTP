package com.example.cli.configuration;

import com.example.cli.CliContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CLITasksConfig {

    @Autowired
    private  ApplicationEventPublisher applicationEventPublisher;


    @Bean
    public List<CLITask<CliContext>> defaultTasks(){

        List<CLITask<CliContext>> tasks = new ArrayList<>();
        CLITask<CliContext> stopTask = new CLITask<CliContext>() {
            @Override
            public void accept(CliContext cliContext) {
                applicationEventPublisher.publishEvent(new StopEvent(this));
            }
        };
        stopTask.setType("stop");

        tasks.add(stopTask);
        return tasks;
    }
}
