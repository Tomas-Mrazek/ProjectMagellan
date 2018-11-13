package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ColorMode {

    CT("ct"), EFFECT("effect");

    @JsonValue
    private final String value;

    private ColorMode(String value) {
        this.value = value;
    }

}
