package cz.jaktoviditoka.projectmagellan.razer.chroma.dto;

import lombok.Getter;

@Getter
public enum DeviceType {

    KEYBOARD("/keyboard"), MOUSE("/mouse"), MOUSEPAD("/mousepad"), HEADSET("/headset"), KEYPAD("/keypad"),
    CHROMA_LINK("/chromalink");

    private final String value;

    private DeviceType(String value) {
        this.value = value;
    }

}
