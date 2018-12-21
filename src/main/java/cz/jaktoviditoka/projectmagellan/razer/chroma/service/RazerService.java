package cz.jaktoviditoka.projectmagellan.razer.chroma.service;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InfoResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InitializeResponse;
import reactor.core.publisher.Mono;

public interface RazerService {

    Mono<InfoResponse> getInto();

    Mono<InitializeResponse> initialize();

    Mono<Void> uninitialize();

}
