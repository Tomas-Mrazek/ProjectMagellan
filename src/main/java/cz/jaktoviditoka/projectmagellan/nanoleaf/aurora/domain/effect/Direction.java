package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.effect;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Direction {

    UP("up"), DOWN("down"), LEFT("left"), RIGHT("right"), OUTWARDS("outwards"), INWARDS("inwards");

    @JsonValue
    private final String value;

    private Direction(String value) {
        this.value = value;
    }

}
