package cz.jaktoviditoka.projectmagellan.razer.chroma.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class HeartbeatResponse {

    int tick;

}
