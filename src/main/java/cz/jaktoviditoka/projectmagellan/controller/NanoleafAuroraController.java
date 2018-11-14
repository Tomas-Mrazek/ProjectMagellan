package cz.jaktoviditoka.projectmagellan.controller;

import org.controlsfx.control.ToggleSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

import cz.jaktoviditoka.projectmagellan.device.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.state.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NanoleafAuroraController {

    private Device device;
    private Set<Device> devices;

    @Autowired
    DeviceModel deviceModel;

    @Autowired
    StateService stateService;

    @FXML
    HBox hboxDevices;

    @FXML
    ToggleSwitch power;

    @FXML
    Slider brightness;

    @FXML
    TabPane colorModeTab;

    @FXML
    Tab whiteTab;

    @FXML
    Tab colorTab;

    @FXML
    Tab effectTab;

    @FXML
    Slider colorTemperature;

    @FXML
    public void initialize() throws IOException {
        devices = deviceModel.getDevices();
        device = devices.iterator().next();

        OnResponse onResponse = stateService.isOn(device);
        power.setSelected(onResponse.isValue());

        BrightnessResponse brightnessResponse = stateService.getBrightness(device);
        brightness.setMin(brightnessResponse.getMin());
        brightness.setMax(brightnessResponse.getMax());
        brightness.setValue(brightnessResponse.getValue());

        ColorTemperatureResponse colorTemperatureResponse = stateService.getColorTemperature(device);
        colorTemperature.setMin(colorTemperatureResponse.getMin());
        colorTemperature.setMax(colorTemperatureResponse.getMax());
        colorTemperature.setValue(colorTemperatureResponse.getValue());

        ColorMode colorMode = stateService.getColorMode(device);
        SelectionModel<Tab> selectionModel = colorModeTab.getSelectionModel();
        switch (colorMode) {
            case COLOR_TEMPERATURE: {
                selectionModel.select(whiteTab);
                break;
            }
            case EFFECT:
                selectionModel.select(effectTab);
                break;
            case HUE_SATURATION:
                selectionModel.select(colorTab);
                break;
            default:
                selectionModel.select(whiteTab);
                break;
        }
    }

    @FXML
    private void changePower() {
        boolean selected = power.isSelected();
        On on = new On(selected);
        OnRequest onRequest = new OnRequest(on);
        stateService.setOn(device, onRequest);
    }

    @FXML
    private void changeBrightness() {
        Double value = brightness.getValue();
        BrightnessValue brightnessValue = new BrightnessValue();
        brightnessValue.setValue(value.intValue());
        BrightnessRequest brightnessRequest = new BrightnessRequest(brightnessValue);
        stateService.setBrightness(device, brightnessRequest);
    }

    @FXML
    private void changeColorTemperature() {
        Double value = colorTemperature.getValue();
        ColorTemperatureValue colorTemperatureValue = new ColorTemperatureValue();
        colorTemperatureValue.setValue(value.intValue());
        ColorTemperatureRequest colorTemperatureRequest = new ColorTemperatureRequest(colorTemperatureValue);
        stateService.setColorTemperature(device, colorTemperatureRequest);
    }

    @FXML
    void discover() throws IOException {
        devices = deviceModel.discover();
    }

    @FXML
    void pair() {
        deviceModel.pair(device);
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

    @FXML
    void addDiscoveredDevice() {
        Image image = new Image("image/nanoleaf-aurora-transparent.png");
        ImageView device = new ImageView(image);
        device.setPreserveRatio(true);
        device.setFitWidth(100);
        // device.fitWidthProperty().bind(hboxDevices.heightProperty());
        // device.fitHeightProperty().bind(hboxDevices.heightProperty());
        hboxDevices.getChildren().add(device);
    }

}
