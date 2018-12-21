package cz.jaktoviditoka.projectmagellan.razer.chroma.service.impl;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InfoResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InitializeResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.RazerService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class RazerServiceImpl implements RazerService {

    private static final String IP = "192.168.1.30";
    private static final int PORT = 55235;
    private static final String PATH = "/razer/chromasdk";
    private static final int TARGET_PORT = 54235;

    @Autowired
    WebClient client;

    @Override
    public Mono<InfoResponse> getInto() {
        URI uri = UriHelper.getUri(IP, PORT, PATH);
        return client
            .method(HttpMethod.GET)
            .uri(uri)
            .header("port", Integer.toString(TARGET_PORT))
            .retrieve()
            .bodyToMono(InfoResponse.class);
    }

    @Override
    public Mono<InitializeResponse> initialize() {
        URI uri = UriHelper.getUri(IP, PORT, PATH);
        Resource body = new ClassPathResource("other/razerServiceInit.json");
        return client
            .method(HttpMethod.POST)
            .uri(uri)
            .header("port", Integer.toString(TARGET_PORT))
            .body(BodyInserters.fromResource(body))
            .retrieve()
            .bodyToMono(InitializeResponse.class);
    }

    @Override
    public Mono<Void> uninitialize() {
        URI uri = UriHelper.getUri(IP, PORT, PATH);
        return client
            .method(HttpMethod.DELETE)
            .uri(uri)
            .header("port", Integer.toString(TARGET_PORT))
            .retrieve()
            .bodyToMono(Void.class);
    }

}
