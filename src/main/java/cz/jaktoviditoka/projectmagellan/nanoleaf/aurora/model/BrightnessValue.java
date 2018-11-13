package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model;

import lombok.Data;

@Data
public class BrightnessValue extends Brightness {

    private int value;
    private int duration;

}
