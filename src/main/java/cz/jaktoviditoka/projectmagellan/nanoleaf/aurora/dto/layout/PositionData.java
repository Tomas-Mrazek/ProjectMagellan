package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.layout;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PositionData {

    long panelId;
    int x;
    int y;
    int o;

}
