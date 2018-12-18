package cz.jaktoviditoka.projectmagellan.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;
import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriHelper {

    public static URI getUri(String ip, int port, String path) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(ip)
            .port(port)
            .path(path)
            .build()
            .toUri();
    }

    public static URI getUri(int port, String path) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(InetAddress.getLoopbackAddress().getHostAddress())
            .port(port)
            .path(path)
            .build()
            .toUri();
    }

}
