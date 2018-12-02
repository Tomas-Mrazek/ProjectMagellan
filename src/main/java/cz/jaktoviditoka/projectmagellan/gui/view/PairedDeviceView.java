package cz.jaktoviditoka.projectmagellan.gui.view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PairedDeviceView extends HBox {

    ImageView image;
    Text name;
    Button unpairButton;

    public void addComponents() {
        getChildren().add(image);
        getChildren().add(name);
        getChildren().add(unpairButton);
    }

}
