package cz.jaktoviditoka.projectmagellan.device;

import java.net.InetAddress;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "uuid")
@Data
public class Device {

    private UUID uuid;
    private String authToken;
    private String name;
    private InetAddress ip;
    private int port;

}
