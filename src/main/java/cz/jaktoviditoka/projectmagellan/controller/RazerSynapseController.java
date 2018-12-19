package cz.jaktoviditoka.projectmagellan.controller;

import com.jfoenix.controls.JFXToggleButton;
import cz.jaktoviditoka.projectmagellan.razer.chroma.model.RazerSynapseModel;
import javafx.fxml.FXML;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Component
public class RazerSynapseController {

    @Autowired
    RazerSynapseModel model;

    @FXML
    JFXToggleButton initializedToggle;

    @FXML
    private void initialize() {
        log.trace("RazerSynapseController initializing...");
        initializedToggle.selectedProperty().bindBidirectional(model.getInitialized());
    }

    @FXML
    private void toggleInitialize() {
        if (initializedToggle.isSelected()) {
            model.enableChromaService();
        } else {
            model.disableChromaService();
        }
    }

}
