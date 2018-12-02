package cz.jaktoviditoka.projectmagellan.utils;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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

}
