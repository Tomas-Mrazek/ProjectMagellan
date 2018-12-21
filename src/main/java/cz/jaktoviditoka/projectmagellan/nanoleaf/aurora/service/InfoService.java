package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.InfoResponse;
import reactor.core.publisher.Mono;

public interface InfoService {

    Mono<InfoResponse> getInfo(NanoleafAuroraDevice device);

}
