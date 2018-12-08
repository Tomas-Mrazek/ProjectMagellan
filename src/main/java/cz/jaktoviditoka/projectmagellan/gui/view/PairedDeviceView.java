package cz.jaktoviditoka.projectmagellan.gui.view;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PairedDeviceView extends GridPane {

    ImageView image;
    Label name;
    Button unpairButton;

    public void addComponents() {
        add(image, 0, 0);
        add(name, 1, 0);
        add(unpairButton, 2, 0);
        setMargin(image, new Insets(5));
        setMargin(name, new Insets(5));
        setMargin(unpairButton, new Insets(5));
        //setGridLinesVisible(true);
        ColumnConstraints left = new ColumnConstraints();
        left.setHgrow(Priority.NEVER);
        ColumnConstraints middle = new ColumnConstraints();
        middle.setHgrow(Priority.ALWAYS);
        ColumnConstraints right = new ColumnConstraints();
        right.setHgrow(Priority.NEVER);
        GridPane.setValignment(unpairButton, VPos.TOP);
        getColumnConstraints().addAll(left, middle, right);
    }

}
