package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class EffectList {

    List<EffectName> value;

}
