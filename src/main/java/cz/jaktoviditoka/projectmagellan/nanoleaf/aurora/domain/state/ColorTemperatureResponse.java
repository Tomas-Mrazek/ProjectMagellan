package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ColorTemperatureResponse {

    int value;
    int max;
    int min;

}
