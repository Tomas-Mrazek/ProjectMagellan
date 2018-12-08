package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.auth.Authorization;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.exception.NotAuthorizedException;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.AuthorizationService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final String BASE_URL = "/api/v1/";
    private static final String NEW = "new";

    @Autowired
    WebClient client;

    @Override
    public Mono<Authorization> addUser(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + NEW);
        return client
            .method(HttpMethod.POST)
            .uri(uri)
            .retrieve()
            .onStatus(
                    HttpStatus.FORBIDDEN::equals,
                    response -> Mono.error(new NotAuthorizedException("Device is not in pairing mode.")))
            .onStatus(
                    HttpStatus.UNPROCESSABLE_ENTITY::equals,
                    response -> Mono.error(new NotAuthorizedException("Unknown error.")))
            .bodyToMono(Authorization.class)
            .log();
    }

    @Override
    public Mono<Void> deleteUser(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken());
        return client
            .method(HttpMethod.DELETE)
            .uri(uri)
            .retrieve()
            .bodyToMono(Void.class);
    }

}
