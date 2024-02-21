package com.example.cli.configuration;

import ch.qos.logback.classic.util.StatusViaSLF4JLoggerFactory;
import com.example.cli.CliContext;
import com.example.cli.configuration.exception.CLIBootstrapException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Component
@Scope("singleton")
@Conditional(WindowsCondition.class)
public class WindowsFileWatcher extends FileWatcher {



    WatchKey watcherKey = null;




    private WatchService watcher;

    @PostConstruct
    public void setUp() {

        try {
            super.setUp();
            watcher = FileSystems.getDefault().newWatchService();
            Path path = Path.of(new URI("file:///" + defaultFolderPath));
            watcherKey = path.register(watcher, ENTRY_CREATE);
            LOGGER.info("Watching for file: {}", path);

        } catch (IOException e) {
            throw new CLIBootstrapException("Cannot create file watcher for windows, please check your set up", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    }


    public void monitorFile(Consumer<Path> onFile) throws InterruptedException {


        WatchKey watchKey;

        if((watchKey=watcher.take())==null){
            return;
        }

        for (WatchEvent<?> event : watchKey.pollEvents()) {

            WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
            LOGGER.info("File found: {}",pathEvent.context());
            Path fileCreated = pathEvent.context();
            onFile.accept(fileCreated);

        }
        watchKey.reset();


    }



}
