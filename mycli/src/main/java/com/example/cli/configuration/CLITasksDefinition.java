package com.example.cli.configuration;

import com.example.cli.CliContext;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Component
@Scope("singleton")
public class CLITasksDefinition {

    private static final Set<CLITask<CliContext>> tasks = new HashSet<>();


    @Autowired
    private List<CLITask<CliContext>> defaultTasks;

    @PostConstruct
    public void postConstruct(){
        tasks.addAll(defaultTasks);
    }

    public Set<CLITask<CliContext>> getTasks() {
        return tasks;
    }
}
