package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.auth.Authorization;
import reactor.core.publisher.Mono;

public interface AuthorizationService {

    Mono<Authorization> addUser(NanoleafAuroraDevice device);

    Mono<Void> deleteUser(NanoleafAuroraDevice device);

}
