package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.auth.Authorization;
import reactor.core.publisher.Mono;

public interface AuthorizationService {

    Mono<Authorization> addUser(Device device);

    Mono<Void> deleteUser(Device device);

}
