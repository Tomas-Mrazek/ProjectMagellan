package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.state;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ColorMode {

    COLOR_TEMPERATURE("ct"), HUE_SATURATION("ht"), EFFECT("effect");

    @JsonValue
    private final String value;

    private ColorMode(String value) {
        this.value = value;
    }

}
