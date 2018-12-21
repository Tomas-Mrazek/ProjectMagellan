package cz.jaktoviditoka.projectmagellan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Data
public abstract class BaseDevice {

    final BaseDeviceType baseDeviceType;
    UUID uuid;
    String name;
    @EqualsAndHashCode.Exclude
    String displayName;
    //TODO Set of Effects

    @JsonIgnore
    public String getResolvedName() {
        if (StringUtils.isEmpty(displayName)) {
            return name;
        }
        return displayName;
    }

}
