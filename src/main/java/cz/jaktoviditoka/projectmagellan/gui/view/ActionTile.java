package cz.jaktoviditoka.projectmagellan.gui.view;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.DeviceModel;
import javafx.scene.layout.VBox;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public abstract class ActionTile extends VBox {

    @Autowired
    DeviceModel deviceModel;

    @EqualsAndHashCode.Include
    private final ActionTileType type;

    public void configure() {
        getStyleClass().add("actionTile");
        setPrefWidth(500);
        setSpacing(5);
    }

}
