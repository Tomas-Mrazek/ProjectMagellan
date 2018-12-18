package cz.jaktoviditoka.projectmagellan.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class MainController {

    @Autowired
    ApplicationContext context;

    @FXML
    VBox root;

    @FXML
    HBox menu;

    @FXML
    Button dashboardButton;

    @FXML
    Button devicesButton;

    @FXML
    BorderPane content;

    VBox nanoleafAurora;
    FlowPane service;
    ServiceController serviceController;

    static final String CSS_BUTTON_SELECTED = "menu-button-selected";

    @FXML
    private void initialize() throws IOException {
        nanoleafAurora = createFxmlLoader("/fxml/Nanoleaf-aurora.fxml").load();
        FXMLLoader serviceLoader = createFxmlLoader("/fxml/Service.fxml");
        service = serviceLoader.load();
        serviceController = serviceLoader.getController();
        dashboardContent();
    }

    @FXML
    public void dashboardContent() {
        menu.getChildren().stream()
            .forEach(el -> el.getStyleClass().remove(CSS_BUTTON_SELECTED));
        menu.getChildren().stream()
            .filter(el -> el.equals(dashboardButton))
            .findAny()
            .ifPresent(el -> el.getStyleClass().add(CSS_BUTTON_SELECTED));
        content.setCenter(nanoleafAurora);
    }

    @FXML
    public void devicesContent() {
        serviceController.showServices();
        menu.getChildren().stream()
            .forEach(el -> el.getStyleClass().remove(CSS_BUTTON_SELECTED));
        menu.getChildren().stream()
            .filter(el -> el.equals(devicesButton))
            .findAny()
            .ifPresent(el -> el.getStyleClass().add(CSS_BUTTON_SELECTED));
        content.setCenter(service);
    }

    private FXMLLoader createFxmlLoader(String location) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
        fxmlLoader.setLocation(getClass().getResource(location));
        return fxmlLoader;
    }

}
