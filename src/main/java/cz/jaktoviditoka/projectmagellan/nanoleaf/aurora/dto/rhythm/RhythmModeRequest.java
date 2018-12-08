package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.rhythm;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RhythmModeRequest {

    RhythmMode rhythmMode;

}
