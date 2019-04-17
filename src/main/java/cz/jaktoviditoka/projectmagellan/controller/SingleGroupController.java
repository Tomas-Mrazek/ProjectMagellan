package cz.jaktoviditoka.projectmagellan.controller;

import cz.jaktoviditoka.projectmagellan.domain.Group;
import cz.jaktoviditoka.projectmagellan.model.GroupModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SingleGroupController {

    @Autowired
    GroupModel model;

    @Getter
    @FXML
    GridPane root;

    @FXML
    ImageView icon;

    @FXML
    TextField name;

    @FXML
    Button deleteGroupButton;

    @FXML
    Button confirmRenameGroupButton;

    @FXML
    Button renameGroupButton;

    @Getter
    @Setter
    Group group;

    @FXML
    private void initialize() {

    }

    @FXML
    private void selectGroup() {

    }

    @FXML
    private void deleteGroup() {
        model.deleteGroup(group);
    }

    @FXML
    private void confirmRenameGroup() {
        confirmRenameGroupButton.setVisible(false);
        renameGroupButton.setVisible(true);
        name.setDisable(true);
        root.setOnMouseClicked(handler -> {
            selectGroup();
        });
        group.setName(name.getText());
    }

    @FXML
    private void renameGroupButton() {
        confirmRenameGroupButton.setVisible(true);
        renameGroupButton.setVisible(false);
        name.setDisable(false);
        root.setOnMouseClicked(null);
    }
}
