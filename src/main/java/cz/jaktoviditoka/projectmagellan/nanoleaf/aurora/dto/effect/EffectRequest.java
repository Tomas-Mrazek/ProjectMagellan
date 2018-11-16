package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class EffectRequest {

    Effect write;

}
