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
import javafx.util.StringConverter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ColorTemperatureTile extends ActionTile {

    final JFXSlider colorTemperature;

    Text tileName;
    Text tileDescription;

    private static final int MIN = 1200;
    private static final int MAX = 6500;

    public ColorTemperatureTile(JFXSlider colorTemperature) {
        super(ActionTileType.BRIGHTNESS);
        this.colorTemperature = colorTemperature;
    }

    @Override
    public void configure() {
        super.configure();
        colorTemperature.setMin(MIN);
        colorTemperature.setMax(MAX);
        colorTemperature.setShowTickLabels(true);
        colorTemperature.setMajorTickUnit(MAX);
        colorTemperature.setPadding(new Insets(20));
        colorTemperature.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n.intValue() == MIN)
                    return MIN + " K";
                if (n.intValue() == MAX)
                    return MAX + " K";
                return n.toString();
            }

            @Override
            public Double fromString(String s) {
                if (s.equals(MIN + "K"))
                    return Double.valueOf(MIN);
                if (s.equals(MAX + "K"))
                    return Double.valueOf(MAX);
                return Double.valueOf(s);
            }
        });

        tileName = new Text("COLOR TEMPERATURE");
        tileName.getStyleClass().add("actionTileName");
        tileName.setBoundsType(TextBoundsType.VISUAL);

        tileDescription = new Text("Changes device color temperature.");
        tileDescription.getStyleClass().add("actionTileDescription");

        getChildren().add(createElement(tileName, 0, 0, HPos.LEFT, Priority.NEVER, VPos.TOP));
        getChildren().add(createElement(tileDescription, 0, 0, HPos.LEFT, Priority.NEVER, VPos.CENTER));
        getChildren().add(createElement(colorTemperature, 0, 0, HPos.CENTER, Priority.ALWAYS, VPos.CENTER));
    }

    private GridPane createElement(Node node, int column, int row, HPos hpos, Priority hpriority, VPos vpos) {
        GridPane pane = new GridPane();
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHalignment(hpos);
        cc.setHgrow(hpriority);
        RowConstraints rc = new RowConstraints();
        rc.setValignment(vpos);
        pane.getColumnConstraints().add(cc);
        pane.getRowConstraints().add(rc);
        pane.add(node, column, row);
        //pane.setGridLinesVisible(true);
        //pane.setStyle("-fx-border-color:white");
        return pane;
    }

}
