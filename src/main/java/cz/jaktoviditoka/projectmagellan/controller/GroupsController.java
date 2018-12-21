package cz.jaktoviditoka.projectmagellan.controller;

import cz.jaktoviditoka.projectmagellan.domain.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class GroupsController {

    ObservableSet<Group> groups;

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
    }

    @FXML
    private void newGroup() {
        Group group = new Group();
        group.setUuid(UUID.randomUUID());
        group.setName("New group");

    }

}
