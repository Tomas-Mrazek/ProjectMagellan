package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model;

import lombok.Data;

@Data
public class ColorTemperatureResponse {

    private int value;
    private int max;
    private int min;

}
