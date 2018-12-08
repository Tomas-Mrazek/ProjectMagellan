package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.rhythm;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AuxActiveResponse {

    @JsonValue
    boolean active;

}
