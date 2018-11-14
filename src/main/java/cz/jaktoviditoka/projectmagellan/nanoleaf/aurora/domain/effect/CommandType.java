package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.effect;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CommandType {

    REQUEST("request"), REQUEST_ALL("requestAll"), DISPLAY("display"), DISPLAY_TEMP("displayTemp"), ADD("add"),
    DELETE("delete"), RENAME("rename");

    @JsonValue
    private final String value;

    private CommandType(String value) {
        this.value = value;
    }

}
