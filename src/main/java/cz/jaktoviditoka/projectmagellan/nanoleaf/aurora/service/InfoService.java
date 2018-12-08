package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.InfoResponse;
import reactor.core.publisher.Mono;

public interface InfoService {

    Mono<InfoResponse> getInfo(Device device);

}
