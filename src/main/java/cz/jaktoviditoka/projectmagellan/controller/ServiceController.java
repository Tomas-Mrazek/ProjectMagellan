package cz.jaktoviditoka.projectmagellan.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ServiceController {

    @Autowired
    ApplicationContext context;

    @FXML
    FlowPane pane;

    @FXML
    VBox nanoleaf;

    @FXML
    VBox razer;

    FlowPane razerSynapse;
    VBox nanoleafAurora;

    @FXML
    private void initialize() {
        log.trace("ServiceController initializing...");
        FXMLLoader loader;

        loader = createFxmlLoader("/fxml/Nanoleaf.fxml");
        try {
            nanoleafAurora = loader.load();
        } catch (IOException e) {
            log.error("Failed to load FXML.", e);
            Platform.exit();
        }

        loader = createFxmlLoader("/fxml/RazerSynapse.fxml");
        try {
            razerSynapse = loader.load();
        } catch (IOException e) {
            log.error("Failed to load FXML.", e);
            Platform.exit();
        }
    }

    @FXML
    private void nanoleafServiceClicked() {
        pane.getChildren().clear();
        pane.getChildren().add(nanoleafAurora);
    }

    @FXML
    private void razerServiceClicked() {
        pane.getChildren().clear();
        pane.getChildren().add(razerSynapse);

    }

    public void showServices() {
        pane.getChildren().clear();
        pane.getChildren().addAll(nanoleaf, razer);
    }

    private FXMLLoader createFxmlLoader(String location) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
        fxmlLoader.setLocation(getClass().getResource(location));
        return fxmlLoader;
    }

}
