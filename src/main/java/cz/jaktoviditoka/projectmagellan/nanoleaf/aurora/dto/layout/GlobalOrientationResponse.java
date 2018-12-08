package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.layout;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class GlobalOrientationResponse {

    int value;
    int max;
    int min;

}
