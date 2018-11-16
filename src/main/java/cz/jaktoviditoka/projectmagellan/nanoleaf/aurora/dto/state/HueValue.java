package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class HueValue extends Hue {

    int value;

}
