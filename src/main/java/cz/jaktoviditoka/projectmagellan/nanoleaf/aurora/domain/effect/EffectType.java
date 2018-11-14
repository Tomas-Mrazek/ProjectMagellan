package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.effect;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EffectType {

    STATIC("static"), RANDOM("random"), FLOW("flow"), WHEEL("wheel"), HIGHLIGHT("highlight"), FADE("fade"),
    EXPLODE("explode"), CUSTOM("custom"), EXTERNAL_CONTROL("extControl");

    @JsonValue
    private final String value;

    private EffectType(String value) {
        this.value = value;
    }

}
