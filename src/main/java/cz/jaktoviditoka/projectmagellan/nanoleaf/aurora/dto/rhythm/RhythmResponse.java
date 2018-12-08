package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.rhythm;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RhythmResponse {

    boolean rhythmConnected;
    boolean rhythmActive;
    int rhythmId;
    String hardwareVersion;
    String firmwareVersion;
    boolean auxAvailable;
    RhythmMode rhythmMode;
    RhythmPositionResponse rhythmPos;

}
