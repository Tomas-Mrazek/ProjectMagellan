package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.layout;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class LayoutResponse {

    int numPanels;
    int sideLength;
    List<PositionData> positionData;

}
