package cz.jaktoviditoka.projectmagellan.razer.chroma.service;

import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.ApplyEffectResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.CreateEffectResponse;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.DeviceType;
import cz.jaktoviditoka.projectmagellan.razer.chroma.dto.EffectRequest;
import reactor.core.publisher.Mono;

public interface DeviceService {

    Mono<ApplyEffectResponse> applyEffect(int targetPort, DeviceType device, EffectRequest request);

    Mono<CreateEffectResponse> createEffect(int targetPort, DeviceType device, EffectRequest request);

}
