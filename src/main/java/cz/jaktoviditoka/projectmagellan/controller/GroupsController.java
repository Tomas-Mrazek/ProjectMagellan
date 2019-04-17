package cz.jaktoviditoka.projectmagellan.controller;

import cz.jaktoviditoka.projectmagellan.domain.Group;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class GroupsController {

    @Autowired
    ApplicationContext context;

    ObservableSet<Group> groups;

    List<SingleGroupController> singleGroupControllers;

    @FXML
    VBox leftWindow;

    @FXML
    Button newGroupButton;

    @FXML
    VBox pairedDeviceGroups;

    @FXML
    ScrollPane actionWindowScroll;

    @FXML
    private void initialize() {
        groups = FXCollections.observableSet(new HashSet<>());
        singleGroupControllers = new ArrayList<>();
        SetChangeListener<Group> listener = change -> {
            if (change.wasAdded()) {
                FXMLLoader loader = createFxmlLoader("/fxml/SingleGroup.fxml");
                try {
                    GridPane groupPane = loader.load();
                    SingleGroupController groupPaneController = loader.getController();
                    groupPaneController.setGroup(change.getElementAdded());
                    singleGroupControllers.add(groupPaneController);
                    pairedDeviceGroups.getChildren().add(groupPane);
                } catch (IOException e) {
                    log.error("Failed to load FXML.", e);
                    Platform.exit();
                }
            } else if (change.wasRemoved()) {
                singleGroupControllers.stream()
                    .filter(element -> element.getGroup().equals(change.getElementRemoved()))
                    .findFirst()
                    .ifPresent(element -> pairedDeviceGroups.getChildren().remove(element.getRoot()));
            }
        };
        groups.addListener(listener);
    }

    @FXML
    private void newGroup() {
        Group group = new Group();
        group.setUuid(UUID.randomUUID());
        group.setName("New group");
        groups.add(group);
    }

    private FXMLLoader createFxmlLoader(String location) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(context::getBean);
        fxmlLoader.setLocation(getClass().getResource(location));
        return fxmlLoader;
    }

}
