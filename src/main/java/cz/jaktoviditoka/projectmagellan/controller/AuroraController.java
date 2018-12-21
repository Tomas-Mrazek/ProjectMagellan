package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class AuroraController {

    @Autowired
    DeviceModel deviceModel;

    @Getter
    @Setter
    NanoleafAuroraDevice device;

    @FXML
    JFXToggleButton powerToggle;

    @FXML
    JFXSlider brightnessSlider;

    @FXML
    JFXSlider colorTempSlider;

    @FXML
    JFXColorPicker colorPicker;

    @FXML
    JFXTabPane colorModeTabPane;

    @FXML
    Tab basicTab;

    @FXML
    Tab colorTab;

    @FXML
    Tab effectTab;

    @FXML
    private void togglePower() {
        deviceModel.setOn(device, powerToggle.isSelected()).subscribe();
    }

    @FXML
    private void changeBrightness() {
        deviceModel.setBrightness(device, brightnessSlider.getValue()).subscribe();
    }

    @FXML
    private void changeColorTemperature() {
        deviceModel.setColorTemperature(device, colorTempSlider.getValue()).subscribe();
    }

    @FXML
    private void changeColor() {
        Mono<Void> monoHue = deviceModel.setHue(device, colorPicker.getValue().getHue());
        Mono<Void> monoSaturation = deviceModel.setSaturation(device, colorPicker.getValue().getSaturation() * 100)
            .delaySubscription(monoHue);
        Mono<Void> monoBrightness = deviceModel.setBrightness(device, colorPicker.getValue().getBrightness() * 100)
            .delaySubscription(monoSaturation);
        monoBrightness.subscribe();
        monoSaturation.subscribe();
        monoHue.subscribe();
    }

    @FXML
    private void initialize() {
        int min = 1200;
        int max = 6500;
        colorTempSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n.intValue() == min)
                    return min + " K";
                if (n.intValue() == max)
                    return max + " K";
                return n.toString();
            }

            @Override
            public Double fromString(String s) {
                if (s.equals(min + "K"))
                    return Double.valueOf(min);
                if (s.equals(max + "K"))
                    return Double.valueOf(max);
                return Double.valueOf(s);
            }
        });
    }

    public void refresh() {
        deviceModel.getInfo(device)
            .log()
            .subscribe(value -> {
                powerToggle.setSelected(value.getState().getOn().isValue());
                brightnessSlider.setValue(value.getState().getBrightness().getValue());
                switch (value.getState().getColorMode()) {
                    case COLOR_TEMPERATURE:
                        colorModeTabPane.getSelectionModel().select(basicTab);
                        break;
                    case HUE_SATURATION:
                        colorModeTabPane.getSelectionModel().select(colorTab);
                        break;
                    case EFFECT:
                        colorModeTabPane.getSelectionModel().select(effectTab);
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
