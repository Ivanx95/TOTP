package com.example.cli.configuration;

import com.example.cli.CliContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public abstract class FileWatcher implements ApplicationListener<StopEvent> {

    protected static Logger LOGGER = LoggerFactory.getLogger(FileWatcher.class);
    protected Boolean watch = true;
    public Integer actionFileNotFoundCounter = 0;
    @Value("${cli.config.file}")
    protected String defaultFolderPath;
    protected String fileName = "totp.FLAG.start";
    protected Path taskPath;

    private CliContext cliContext = new CliContext();

    public void setUp() throws URISyntaxException {
        taskPath = Path.of(new URI("file:///" + defaultFolderPath.concat("/").concat("actions.totp")));
    }

    @Autowired
    private CLITasksDefinition cliTasksStore;


    public void watch() throws InterruptedException {
        while (watch) {
            monitorFile(this::onReceivedFile);
        }
    }

    abstract void monitorFile(Consumer<Path> onFile) throws InterruptedException;


    public void onReceivedFile(Path fileCreated) {
        String fileName = fileCreated.getFileName().toString();

        if (!fileName.equals(this.fileName)) {
            return;
        }


        try {
            String action = new String(Files.readAllBytes(taskPath)).trim();

            LOGGER.info("Action {}", action);


            cliTasksStore.getTasks().stream().filter(e -> e.getType().equalsIgnoreCase(action))
                    .findFirst().ifPresent(a -> a.accept(cliContext));

        } catch (IOException e) {
            //No action file available skip
            LOGGER.warn("Something bad happended", e);
            actionFileNotFoundCounter++;
            if (actionFileNotFoundCounter > 5) {
                watch = false;
            }
        }

    }


    @Override
    public void onApplicationEvent(StopEvent event) {
        LOGGER.info("Stopping file watcher");
        watch = false;
    }
}
