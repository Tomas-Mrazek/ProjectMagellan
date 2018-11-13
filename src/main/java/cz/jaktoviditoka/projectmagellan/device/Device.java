package cz.jaktoviditoka.projectmagellan.device;

import java.net.InetAddress;

import lombok.Data;

@Data
public class Device {

    private final String authToken = "4WiXtaccd3SZGQzl3OnYfAS86WYlKjx8";

    private String name;
    private InetAddress ip;
    private int port;

}
