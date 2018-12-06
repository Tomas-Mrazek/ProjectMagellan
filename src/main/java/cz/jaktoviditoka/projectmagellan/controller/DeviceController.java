package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import cz.jaktoviditoka.projectmagellan.gui.view.BrightnessTile;
import cz.jaktoviditoka.projectmagellan.gui.view.ColorTemperatureTile;
import cz.jaktoviditoka.projectmagellan.gui.view.ColorTile;
import cz.jaktoviditoka.projectmagellan.gui.view.PowerTile;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DeviceController {

    @NonNull
    final DeviceModel deviceModel;

    @EqualsAndHashCode.Include
    @NonNull
    final Device device;

    PowerTile powerTile;
    BrightnessTile brightnessTile;
    ColorTile colorTile;
    ColorTemperatureTile colorTemperatureTile;

    public void init() {
        JFXToggleButton onButton = new JFXToggleButton();
        powerTile = new PowerTile(onButton);
        powerTile.configure();
        onButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            deviceModel.setOn(device, newValue).subscribe();
        });

        JFXSlider brightnessSlider = new JFXSlider();
        brightnessTile = new BrightnessTile(brightnessSlider);
        brightnessTile.configure();
        brightnessSlider.setOnMouseReleased(event -> {
            deviceModel.setBrightness(device, brightnessSlider.getValue()).subscribe();
        });

        JFXColorPicker colorPicker = new JFXColorPicker();
        colorTile = new ColorTile(colorPicker);
        colorTile.configure();
        colorPicker.setOnAction(event -> {
            Mono<Void> monoHue = deviceModel.setHue(device, colorPicker.getValue().getHue());
            Mono<Void> monoSaturation = deviceModel.setSaturation(device, colorPicker.getValue().getSaturation() * 100)
                .delaySubscription(monoHue);
            Mono<Void> monoBrightness = deviceModel.setBrightness(device, colorPicker.getValue().getBrightness() * 100)
                .delaySubscription(monoSaturation);
            monoHue.subscribe();
            monoSaturation.subscribe();
            monoBrightness.subscribe();
        });

        JFXSlider colorTempSlider = new JFXSlider();
        colorTemperatureTile = new ColorTemperatureTile(colorTempSlider);
        colorTemperatureTile.configure();
        colorTempSlider.setOnMouseReleased(event -> {
            deviceModel.setColorTemperature(device, colorTempSlider.getValue()).subscribe();
        });
    }

    public void refresh() {

        JFXToggleButton onButton = powerTile.getPower();
        JFXSlider brightnessSlider = brightnessTile.getBrightness();
        JFXColorPicker colorPicker = colorTile.getColor();
        JFXSlider colorTempSlider = colorTemperatureTile.getColorTemperature();

        deviceModel.isOn(device)
            .map(OnResponse::isValue)
            .subscribe(onButton::setSelected);

        deviceModel.getBrightness(device)
            .map(BrightnessResponse::getValue)
            .subscribe(brightnessSlider::setValue);

        deviceModel.getColorTemperature(device)
            .map(ColorTemperatureResponse::getValue)
            .subscribe(colorTempSlider::setValue);

        Mono<BrightnessResponse> monoBrightness = deviceModel.getBrightness(device);
        Mono<HueResponse> monoHue = deviceModel.getHue(device).delaySubscription(monoBrightness);
        Mono<SaturationResponse> monoSaturation = deviceModel.getSaturation(device).delaySubscription(monoHue);
        Mono
            .zip(monoHue, monoSaturation, monoBrightness)
            .map(map -> map.mapT1(mapper -> Double.valueOf(mapper.getValue())))
            .map(map -> map.mapT2(mapper -> Double.valueOf(mapper.getValue()) / 100))
            .map(map -> map.mapT3(mapper -> Double.valueOf(mapper.getValue()) / 100))
            .map(map -> Color.hsb(map.getT1(), map.getT2(), map.getT3()))
            .subscribe(colorPicker::setValue);

    }

}
