package cz.jaktoviditoka.projectmagellan.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.net.InetAddress;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class NanoleafAuroraDevice extends BaseDevice {

    InetAddress ip;
    int port;
    @EqualsAndHashCode.Exclude
    String authToken;

    @JsonCreator
    public NanoleafAuroraDevice(@JsonProperty("baseDeviceType") BaseDeviceType baseDeviceType) {
        super(baseDeviceType);
    }

}