package cz.jaktoviditoka.projectmagellan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public abstract class BaseDevice {

    final BaseDeviceType baseDeviceType;
    @EqualsAndHashCode.Include
    UUID uuid;
    String name;
    String displayName;
    InetAddress ip;
    int port;

    @JsonIgnore
    public String getResolvedName() {
        if (StringUtils.isEmpty(displayName)) {
            return name;
        }
        return displayName;
    }

}
