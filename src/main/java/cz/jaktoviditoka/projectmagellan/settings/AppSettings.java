package cz.jaktoviditoka.projectmagellan.settings;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import cz.jaktoviditoka.projectmagellan.device.Device;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppSettings {

    private Settings settings;
    private final File file = new File("magellan-config.json");

    @Autowired
    private ObjectMapper mapper;

    @PostConstruct
    private void init() throws JsonParseException, JsonMappingException, IOException {
        loadSettings();
    }

    public void saveSettings() throws JsonGenerationException, JsonMappingException, IOException {
        log.debug("saving settings");
        mapper.writeValue(file, settings);
    }

    private void loadSettings() throws JsonParseException, JsonMappingException, IOException {
        log.debug("loading settings");
        if (file.exists()) {
            settings = mapper.readValue(file, Settings.class);
        } else {
            file.createNewFile();
            createSettings();
        }
    }

    private void createSettings() {
        settings = new Settings();
        settings.setDevices(new HashSet<>());
    }

    public Set<Device> getDevices() {
        return settings.getDevices();
    }

}
