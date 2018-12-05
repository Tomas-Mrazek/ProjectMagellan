package cz.jaktoviditoka.projectmagellan.gui.view;

import com.jfoenix.controls.JFXSlider;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
public class BrightnessTile extends ActionTile {

    final JFXSlider brightness;

    Text tileName;
    Text tileDescription;

    private static final int MIN = 0;
    private static final int MAX = 100;

    public BrightnessTile(JFXSlider brightness) {
        super(ActionTileType.BRIGHTNESS);
        this.brightness = brightness;
    }

    @Override
    public void configure() {
        super.configure();
        brightness.setMin(MIN);
        brightness.setMax(MAX);
        brightness.setShowTickLabels(true);
        brightness.setMajorTickUnit(MAX);
        brightness.setPadding(new Insets(20));

        tileName = new Text("BRIGHTNESS");
        tileName.getStyleClass().add("actionTileName");
        tileName.setBoundsType(TextBoundsType.VISUAL);

        tileDescription = new Text("Changes device brightness.");
        tileDescription.getStyleClass().add("actionTileDescription");

        getChildren().add(createElement(tileName, 0, 0, HPos.LEFT, Priority.NEVER, VPos.TOP));
        getChildren().add(createElement(tileDescription, 0, 0, HPos.LEFT, Priority.NEVER, VPos.CENTER));
        getChildren().add(createElement(brightness, 0, 0, HPos.CENTER, Priority.ALWAYS, VPos.CENTER));
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
