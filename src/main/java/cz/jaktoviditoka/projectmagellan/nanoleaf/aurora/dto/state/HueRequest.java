package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class HueRequest {

    Hue hue;

}
