package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class EffectsNameRequest {

    String select;

}
