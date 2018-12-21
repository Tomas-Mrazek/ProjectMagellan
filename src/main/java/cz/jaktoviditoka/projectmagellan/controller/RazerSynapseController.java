package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXToggleButton;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.DeviceType;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InitializeResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.model.RazerSynapseModel;
import javafx.fxml.FXML;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class RazerSynapseController {

    @Autowired
    RazerSynapseModel model;

    @FXML
    JFXToggleButton initializedToggle;

    @FXML
    JFXColorPicker keyboardColorPicker;

    @FXML
    JFXColorPicker mouseColorPicker;

    @FXML
    JFXColorPicker chromaLinkColorPicker;

    @FXML
    private void initialize() {
        log.trace("RazerSynapseController initializing...");
        keyboardColorPicker.disableProperty().bind(model.getInitialized().not());
        mouseColorPicker.disableProperty().bind(model.getInitialized().not());
        chromaLinkColorPicker.disableProperty().bind(model.getInitialized().not());
        log.trace("RazerSynapseController initialized.");
    }

    @FXML
    private void toggleInitialize() {
        initializedToggle.setDisable(true);
        if (initializedToggle.isSelected()) {
            model.enableChromaService()
                .map(InitializeResponse::getSessionid)
                .map(Integer::valueOf)
                .log()
                .subscribeOn(Schedulers.elastic())
                .subscribe(
                        consumer -> {
                            model.setPort(consumer);
                        }, error -> {
                            model.getInitialized().set(false);
                            initializedToggle.setSelected(false);
                            initializedToggle.setDisable(false);
                            log.warn("Unable to initialize Razer Chroma service.", error);
                        }, () -> {
                            model.getInitialized().set(true);
                            model.keepAliveService();
                            initializedToggle.setDisable(false);
                        });
        } else {
            model.disableChromaService()
                .log()
                .subscribeOn(Schedulers.elastic())
                .subscribe(
                        consumer -> {
                        }, error -> {
                            model.getInitialized().set(true);
                            initializedToggle.setSelected(true);
                            initializedToggle.setDisable(false);
                            log.warn("Unable to uninitialize Razer Chroma service. It may already be disabled.", error);
                        }, () -> {
                            model.getInitialized().set(false);
                            initializedToggle.setDisable(false);
                        });
        }
    }

    @FXML
    private void changeKeyboardColor() {
        model.changeDeviceColor(keyboardColorPicker.getValue(), DeviceType.KEYBOARD);
    }

    @FXML
    private void changeMouseColor() {
        model.changeDeviceColor(mouseColorPicker.getValue(), DeviceType.MOUSE);
    }

    @FXML
    private void changeChromaLinkColor() {
        model.changeDeviceColor(chromaLinkColorPicker.getValue(), DeviceType.CHROMA_LINK);
    }

}
