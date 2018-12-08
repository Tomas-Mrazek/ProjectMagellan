package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.InfoResponse;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.InfoService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class InfoServiceImpl implements InfoService {

    private static final String BASE_URL = "/api/v1/";

    @Autowired
    WebClient client;

    @Override
    public Mono<InfoResponse> getInfo(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken());
        return client
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToMono(InfoResponse.class);
    }

}
