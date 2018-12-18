package cz.jaktoviditoka.projectmagellan.razer.chroma.service;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.InitializeResponse;
import reactor.core.publisher.Mono;

public interface RazerService {

    Mono<InitializeResponse> initialize();

    Mono<Void> uninitialize();

}
