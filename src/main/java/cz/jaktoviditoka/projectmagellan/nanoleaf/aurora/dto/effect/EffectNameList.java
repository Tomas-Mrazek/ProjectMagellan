package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class EffectNameList {

    List<EffectName> effects = new ArrayList<>();

}
