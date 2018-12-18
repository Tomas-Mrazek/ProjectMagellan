package cz.jaktoviditoka.projectmagellan.razer.chroma.service.impl;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.HeartbeatResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.HeartbeatService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class HeartbeatServiceImpl implements HeartbeatService {

    private static final String IP = "192.168.1.30";
    private static final int PORT = 55235;
    private static final String BASE_URL = "/chromasdk";
    private static final String HEARTBEAT = "/heartbeat";

    @Autowired
    WebClient client;

    @Override
    public Mono<HeartbeatResponse> heartbeat(int targetPort) {
        URI uri = UriHelper.getUri(IP, PORT, BASE_URL + HEARTBEAT);
        return client
            .method(HttpMethod.PUT)
            .uri(uri)
            .header("port", Integer.toString(targetPort))
            .retrieve()
            .bodyToMono(HeartbeatResponse.class);
    }


}
