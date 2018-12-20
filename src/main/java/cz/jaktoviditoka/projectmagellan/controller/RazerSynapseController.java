package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXToggleButton;
import cz.jaktoviditoka.projectmagellan.razer.chroma.model.RazerSynapseModel;
import javafx.fxml.FXML;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class RazerSynapseController {

    @Autowired
    RazerSynapseModel model;

    @FXML
    JFXToggleButton initializedToggle;

    @FXML
    JFXColorPicker colorPicker;

    @FXML
    private void initialize() {
        log.trace("RazerSynapseController initializing...");
        initializedToggle.selectedProperty().bindBidirectional(model.getInitialized());
        colorPicker.disableProperty().bind(initializedToggle.selectedProperty().not());
    }

    @FXML
    private void toggleInitialize() {
        if (initializedToggle.isSelected()) {
            model.enableChromaService();
        } else {
            model.disableChromaService();
        }
    }

    @FXML
    private void changeKeyboardColor() {
        model.changeKeyboardColor(colorPicker.getValue());
    }

}
