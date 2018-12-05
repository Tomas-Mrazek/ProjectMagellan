package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import cz.jaktoviditoka.projectmagellan.gui.view.BrightnessTile;
import cz.jaktoviditoka.projectmagellan.gui.view.ColorTemperatureTile;
import cz.jaktoviditoka.projectmagellan.gui.view.PowerTile;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.BrightnessResponse;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.ColorTemperatureResponse;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.OnResponse;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DeviceController {

    @NonNull
    final DeviceModel deviceModel;

    @EqualsAndHashCode.Include
    @NonNull
    final Device device;

    PowerTile on;
    BrightnessTile brightness;
    ColorTemperatureTile colorTemperature;

    public void init() {
        JFXToggleButton onButton = new JFXToggleButton();
        on = new PowerTile(onButton);
        on.configure();
        onButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            deviceModel.setOn(device, newValue);
        });

        JFXSlider brightnessSlider = new JFXSlider();
        brightness = new BrightnessTile(brightnessSlider);
        brightness.configure();
        brightnessSlider.setOnMouseReleased(event -> {
            deviceModel.setBrightness(device, brightnessSlider.getValue());
        });

        JFXSlider colorTempSlider = new JFXSlider();
        colorTemperature = new ColorTemperatureTile(colorTempSlider);
        colorTemperature.configure();
        colorTempSlider.setOnMouseReleased(event -> {
            deviceModel.setColorTemperature(device, colorTempSlider.getValue());
        });
    }
    
    public void refresh() {
        OnResponse onResponse = deviceModel.isOn(device);
        JFXToggleButton onButton = on.getPower();
        onButton.setSelected(onResponse.isValue());

        BrightnessResponse brightnessResponse = deviceModel.getBrightness(device);
        JFXSlider brightnessSlider = brightness.getBrightness();
        brightnessSlider.setValue(brightnessResponse.getValue());

        ColorTemperatureResponse colorTemperatureResponse = deviceModel.getColorTemperature(device);
        JFXSlider colorTempSlider = colorTemperature.getColorTemperature();
        colorTempSlider.setValue(colorTemperatureResponse.getValue());
    }

}
