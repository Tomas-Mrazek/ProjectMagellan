package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ColorMode {

    COLOR_TEMPERATURE("ct"), HUE_SATURATION("hs"), EFFECT("effect");

    @JsonValue
    private final String value;

    private ColorMode(String value) {
        this.value = value;
    }

}
