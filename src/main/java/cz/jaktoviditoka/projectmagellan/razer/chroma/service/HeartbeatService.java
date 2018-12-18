package cz.jaktoviditoka.projectmagellan.razer.chroma.service;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.HeartbeatResponse;
import reactor.core.publisher.Mono;

public interface HeartbeatService {

    Mono<HeartbeatResponse> heartbeat(int port);

}
