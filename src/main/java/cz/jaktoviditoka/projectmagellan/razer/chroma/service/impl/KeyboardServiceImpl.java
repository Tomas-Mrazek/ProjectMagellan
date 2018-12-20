package cz.jaktoviditoka.projectmagellan.razer.chroma.service.impl;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.ApplyEffectResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.CreateEffectResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.EffectRequest;
import cz.jaktoviditoka.projectmagellan.razer.chroma.service.KeyboardService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class KeyboardServiceImpl implements KeyboardService {

    private static final String IP = "192.168.1.30";
    private static final int PORT = 55235;
    private static final String BASE_URL = "/chromasdk";
    private static final String KEYBOARD = "/keyboard";

    @Autowired
    WebClient client;

    @Override
    public Mono<ApplyEffectResponse> applyEffect(int targetPort, EffectRequest request) {
        URI uri = UriHelper.getUri(IP, PORT, BASE_URL + KEYBOARD);
        return client
            .method(HttpMethod.PUT)
            .uri(uri)
            .header("port", Integer.toString(targetPort))
            .body(BodyInserters.fromObject(request))
            .retrieve()
            .bodyToMono(ApplyEffectResponse.class);
    }

    @Override
    public Mono<CreateEffectResponse> createEffect(int targetPort, EffectRequest request) {
        URI uri = UriHelper.getUri(IP, PORT, BASE_URL + KEYBOARD);
        return client
            .method(HttpMethod.POST)
            .uri(uri)
            .header("port", Integer.toString(targetPort))
            .body(BodyInserters.fromObject(request))
            .retrieve()
            .bodyToMono(CreateEffectResponse.class);
    }

}
