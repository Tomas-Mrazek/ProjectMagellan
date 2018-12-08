package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class EffectsResponse {

    String select;
    List<String> effectsList;

}
