package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect;

import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PaletteColor {

    @Size(min = 0, max = 359)
    int hue;

    @Size(min = 0, max = 100)
    int saturation;

    @Size(min = -1, max = 100)
    int brightness;

    int probability;

}
