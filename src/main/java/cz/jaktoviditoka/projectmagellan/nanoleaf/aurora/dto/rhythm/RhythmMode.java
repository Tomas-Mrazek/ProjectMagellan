package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.rhythm;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RhythmMode {

    MICROPHONE(0), AUX(1);

    @JsonValue
    private final int mode;

    private RhythmMode(int mode) {
        this.mode = mode;
    }

}
