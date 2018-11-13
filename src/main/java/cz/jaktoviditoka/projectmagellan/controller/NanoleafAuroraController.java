package cz.jaktoviditoka.projectmagellan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

import cz.jaktoviditoka.projectmagellan.device.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.On;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.OnRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import cz.jaktoviditoka.projectmagellan.ssdp.SSDPClient;
import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NanoleafAuroraController {

    Set<Device> devices;

    @Autowired
    StateService stateService;

    @Autowired
    SSDPClient ssdpclient;

    @FXML
    void discover() throws IOException {
        devices = ssdpclient.mSearch();
    }

    @FXML
    void isOn() {
        devices.forEach(d -> {
            log.debug("isOn -> \n{}", stateService.isOn(d));
        });
    }

    @FXML
    void setOn() {
        OnRequest onRequest = new OnRequest();
        onRequest.setOn(new On(false));
        devices.forEach(d -> {
            log.debug("setOn -> \n{}", onRequest);
            stateService.setOn(d, onRequest);
        });
    }

    @FXML
    void getBrightness() {
        devices.forEach(d -> {
            log.debug("getBrightness -> \n{}", stateService.getBrightness(d));
        });
    }

    @FXML
    void setBrightness() {
        throw new UnsupportedOperationException();
    }

    @FXML
    void getHue() {
        devices.forEach(d -> {
            log.debug("getHue -> \n{}", stateService.getHue(d));
        });
    }

    @FXML
    void setHue() {
        throw new UnsupportedOperationException();
    }

    @FXML
    void getSaturation() {
        devices.forEach(d -> {
            log.debug("getSaturation -> \n{}", stateService.getSaturation(d));
        });
    }

    @FXML
    void setSaturation() {
        throw new UnsupportedOperationException();
    }

    @FXML
    void getColorTemperature() {
        devices.forEach(d -> {
            log.debug("getColorTemperature -> \n{}", stateService.getColorTemperature(d));
        });
    }

    @FXML
    void setColorTemperature() {
        throw new UnsupportedOperationException();
    }

    @FXML
    void getColorMode() {
        devices.forEach(d -> {
            log.debug("getColorMode -> \n{}", stateService.getColorMode(d));
        });
    }

}
