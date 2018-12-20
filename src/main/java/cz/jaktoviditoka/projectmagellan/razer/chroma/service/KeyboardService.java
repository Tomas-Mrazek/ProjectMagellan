package cz.jaktoviditoka.projectmagellan.razer.chroma.service;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.ApplyEffectResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.CreateEffectResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.EffectRequest;
import reactor.core.publisher.Mono;

public interface KeyboardService {

    Mono<ApplyEffectResponse> applyEffect(int targetPort, EffectRequest request);

    Mono<CreateEffectResponse> createEffect(int targetPort, EffectRequest request);

}
