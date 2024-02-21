package com.example.cli.configuration;

import org.springframework.context.ApplicationEvent;

public class StopEvent extends ApplicationEvent {
    public StopEvent(Object source) {
        super(source);
    }


}
