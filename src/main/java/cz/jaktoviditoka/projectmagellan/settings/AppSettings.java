package cz.jaktoviditoka.projectmagellan.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppSettings {

    private Settings settings;
    private final File file = new File("magellan-settings.json");

    @Autowired
    private ObjectMapper mapper;

    @PostConstruct
    private void init() throws IOException {
        loadSettings();
    }

    public void saveSettings() throws IOException {
        log.debug("saving settings -> \n{}", settings);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, settings);
    }

    private void loadSettings() throws IOException {
        if (file.exists()) {
            try {
                settings = mapper.readValue(file, Settings.class);
                log.debug("loading settings -> \n{}", settings);
            } catch (MismatchedInputException e) {
                log.error("Could not load settings.", e);
            }
        } else {
            file.createNewFile();
            createSettings();
        }
    }

    private void createSettings() {
        log.debug("creating settings");
        settings = new Settings();
        settings.setDevices(new HashSet<>());
    }

    public Set<NanoleafAuroraDevice> getDevices() {
        if (settings.getDevices() == null) {
            settings.setDevices(new HashSet<>());
        }
        return settings.getDevices();
    }

}
