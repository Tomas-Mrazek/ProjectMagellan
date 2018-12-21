package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.Effect;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.EffectsNameRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.WriteRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.EffectsService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class EffectsServiceImpl implements EffectsService {

    private static final String BASE_URL = "/api/v1/";
    private static final String EFFECTS_NAME = "/effects/select";
    private static final String EFFECTS_LIST = "/effects/effectsList";

    @Override
    public Mono<String> getCurrentEffect(NanoleafAuroraDevice device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + EFFECTS_NAME);
        return WebClient.create()
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToMono(String.class);
    }

    @Override
    public Mono<Void> setCurrentEffect(NanoleafAuroraDevice device, EffectsNameRequest effectName) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + EFFECTS_NAME);
        return WebClient.create()
            .method(HttpMethod.PUT)
            .uri(uri)
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public Flux<String> getEffects(NanoleafAuroraDevice device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + EFFECTS_LIST);
        return WebClient.create()
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToFlux(String.class);
    }

    @Override
    public Mono<Effect> createEffect(NanoleafAuroraDevice device, WriteRequest effect) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + EFFECTS_NAME);
        return WebClient.create()
            .method(HttpMethod.PUT)
            .uri(uri)
            .retrieve()
            .bodyToMono(Effect.class);
    }

}
