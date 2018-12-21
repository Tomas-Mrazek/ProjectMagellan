package cz.jaktoviditoka.projectmagellan.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RazerGenericDevice extends BaseDevice {
    
    @JsonCreator
    public RazerGenericDevice(@JsonProperty("baseDeviceType") BaseDeviceType baseDeviceType) {
        super(baseDeviceType);
    }

}
