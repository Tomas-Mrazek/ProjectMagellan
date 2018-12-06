package cz.jaktoviditoka.projectmagellan.gui.view;

import com.jfoenix.controls.JFXColorPicker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ColorTile extends ActionTile {

    final JFXColorPicker color;

    Text tileName;
    Text tileDescription;

    public ColorTile(JFXColorPicker color) {
        super(ActionTileType.COLOR);
        this.color = color;
    }

    @Override
    public void configure() {
        super.configure();

        tileName = new Text("COLOR");
        tileName.getStyleClass().add("actionTileName");
        tileName.setBoundsType(TextBoundsType.VISUAL);

        tileDescription = new Text("Changes device color.");
        tileDescription.getStyleClass().add("actionTileDescription");

        getChildren().add(createElement(tileName, 0, 0, HPos.LEFT, Priority.NEVER, VPos.TOP));
        getChildren().add(createElement(tileDescription, 0, 0, HPos.LEFT, Priority.NEVER, VPos.CENTER));
        getChildren().add(createElement(color, 0, 0, HPos.CENTER, Priority.ALWAYS, VPos.CENTER));
    }

    private GridPane createElement(Node node, int column, int row, HPos hpos, Priority hpriority, VPos vpos) {
        GridPane pane = new GridPane();
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHalignment(hpos);
        cc.setHgrow(hpriority);
        RowConstraints rc = new RowConstraints();
        rc.setValignment(vpos);
        //pane.setGridLinesVisible(true);
        pane.getColumnConstraints().add(cc);
        pane.getRowConstraints().add(rc);
        pane.add(node, column, row);
        //pane.setStyle("-fx-border-color:white");
        return pane;
    }

}
