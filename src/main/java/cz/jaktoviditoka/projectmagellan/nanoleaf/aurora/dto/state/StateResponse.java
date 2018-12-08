package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class StateResponse {

    OnResponse on;
    BrightnessResponse brightness;
    HueResponse hue;
    SaturationResponse sat;
    ColorTemperatureResponse ct;
    ColorMode colorMode;

}
