package cz.jaktoviditoka.projectmagellan.domain;

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
    InetAddress ip;
    int port;

}
