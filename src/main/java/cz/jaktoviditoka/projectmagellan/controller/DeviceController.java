package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import cz.jaktoviditoka.projectmagellan.gui.view.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import javafx.scene.control.Tab;
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

    ActionTabPane colorModeTab;
    Tab whiteTab;
    Tab colorTab;
    Tab effectTab;

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
            monoBrightness.subscribe();
            monoSaturation.subscribe();
            monoHue.subscribe();
        });

        JFXSlider colorTempSlider = new JFXSlider();
        colorTemperatureTile = new ColorTemperatureTile(colorTempSlider);
        colorTemperatureTile.configure();
        colorTempSlider.setOnMouseReleased(event -> {
            deviceModel.setColorTemperature(device, colorTempSlider.getValue()).subscribe();
        });

        whiteTab = new Tab("BASIC");
        whiteTab.setContent(colorTemperatureTile);

        colorTab = new Tab("COLOR");
        colorTab.setContent(colorTile);

        effectTab = new Tab("EFFECT");

        colorModeTab = new ActionTabPane();
        colorModeTab.getTabs().addAll(whiteTab, colorTab, effectTab);
    }

    public void refresh() {

        JFXToggleButton onButton = powerTile.getPower();
        JFXSlider brightnessSlider = brightnessTile.getBrightness();
        JFXColorPicker colorPicker = colorTile.getColor();
        JFXSlider colorTempSlider = colorTemperatureTile.getColorTemperature();

        deviceModel.getInfo(device)
            .log()
            .subscribe(value -> {
                onButton.setSelected(value.getState().getOn().isValue());
                brightnessSlider.setValue(value.getState().getBrightness().getValue());
                switch (value.getState().getColorMode()) {
                    case COLOR_TEMPERATURE:
                        colorModeTab.getSelectionModel().select(whiteTab);
                        break;
                    case HUE_SATURATION:
                        colorModeTab.getSelectionModel().select(colorTab);
                        break;
                    case EFFECT:
                        colorModeTab.getSelectionModel().select(effectTab);
                        break;
                    default:
                        break;
                }
                colorTempSlider.setValue(value.getState().getCt().getValue());
                colorPicker.setValue(Color.hsb(
                        value.getState().getHue().getValue(),
                        value.getState().getSat().getValue() / 100d,
                        value.getState().getBrightness().getValue() / 100d));
            });

    }

}
