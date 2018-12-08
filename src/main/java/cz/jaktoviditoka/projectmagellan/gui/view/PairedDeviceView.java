package cz.jaktoviditoka.projectmagellan.gui.view;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PairedDeviceView extends GridPane {

    ImageView image;
    TextField name;
    Button unpairButton;
    Button confirmButton;
    Button editButton;

    public void addComponents() {
        add(image, 0, 0, 1, 3);
        add(name, 1, 0, 1, 3);
        add(unpairButton, 2, 0);
        add(confirmButton, 2, 1);
        add(editButton, 2, 2);
        setMargin(image, new Insets(10));
        setMargin(name, new Insets(5));
        //setMargin(confirmButton, new Insets(5));
        //setMargin(unpairButton, new Insets(5));
        //setMargin(editButton, new Insets(5));
        //setGridLinesVisible(true);
        ColumnConstraints left = new ColumnConstraints();
        left.setHgrow(Priority.NEVER);
        ColumnConstraints middle = new ColumnConstraints();
        middle.setHgrow(Priority.ALWAYS);
        ColumnConstraints right = new ColumnConstraints();
        right.setHgrow(Priority.NEVER);
        RowConstraints first = new RowConstraints();
        first.setPercentHeight(100 / 3d);
        RowConstraints second = new RowConstraints();
        second.setPercentHeight(100 / 3d);
        RowConstraints third = new RowConstraints();
        third.setPercentHeight(100 / 3d);
        setValignment(unpairButton, VPos.TOP);
        setValignment(confirmButton, VPos.CENTER);
        setValignment(editButton, VPos.BOTTOM);
        getColumnConstraints().addAll(left, middle, right);
        getRowConstraints().addAll(first, second, third);
    }

}
