package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model;

import lombok.Data;

@Data
public class StateResponse {

    private OnResponse on;
    private BrightnessResponse brightness;
    private HueResponse hue;
    private SaturationResponse sat;
    private ColorTemperature ct;
    private ColorMode colorMode;

}
