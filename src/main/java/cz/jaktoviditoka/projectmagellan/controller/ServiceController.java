package cz.jaktoviditoka.projectmagellan.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ServiceController {

    @Autowired
    ApplicationContext context;

    @FXML
    FlowPane pane;

    List<Node> services;

    VBox razerSynapse;

    @FXML
    private void initialize() {
        ImageView nanoleafLogo = new ImageView("image/nanoleaf-logo.png");
        nanoleafLogo.setFitWidth(150);
        nanoleafLogo.setPreserveRatio(true);
        nanoleafLogo.setSmooth(true);
        Text nanoleafText = new Text("NANOLEAF");
        VBox nanoleaf = new VBox(nanoleafLogo, nanoleafText);
        nanoleaf.getStyleClass().add("tile");
        nanoleaf.setSpacing(20);
        nanoleaf.setAlignment(Pos.CENTER);

        ImageView razerLogo = new ImageView("image/razer-logo.png");
        razerLogo.setFitWidth(150);
        razerLogo.setPreserveRatio(true);
        razerLogo.setSmooth(true);
        Text razerText = new Text("RAZER");
        VBox razer = new VBox(razerLogo, razerText);
        razer.getStyleClass().add("tile");
        razer.setSpacing(20);
        razer.setAlignment(Pos.CENTER);
        razer.setOnMouseClicked(event -> {
            try {
                razerSynapse = createFxmlLoader("/fxml/RazerSynapse.fxml").load();
                pane.getChildren().clear();
                pane.getChildren().add(razerSynapse);
            } catch (IOException e) {
                log.error("Failed to load FXML.", e);
                Platform.exit();
            }
        });

        services = new ArrayList<>();
        services.add(nanoleaf);
        services.add(razer);

        pane.getChildren().addAll(services);
    }

    public void showServices() {
        pane.getChildren().clear();
        pane.getChildren().addAll(services);
    }

    private FXMLLoader createFxmlLoader(String location) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
        fxmlLoader.setLocation(getClass().getResource(location));
        return fxmlLoader;
    }

}
