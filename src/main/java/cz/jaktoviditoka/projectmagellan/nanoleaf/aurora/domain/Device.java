package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jaktoviditoka.projectmagellan.domain.BaseDevice;
import cz.jaktoviditoka.projectmagellan.domain.BaseDeviceType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Device extends BaseDevice {

    @EqualsAndHashCode.Exclude
    String authToken;

    @JsonCreator
    public Device(@JsonProperty("baseDeviceType") BaseDeviceType baseDeviceType) {
        super(baseDeviceType);
    }

}