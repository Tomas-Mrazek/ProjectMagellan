package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.Effect;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.EffectsNameRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.WriteRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EffectsService {

    Mono<String> getCurrentEffect(Device device);

    Mono<Void> setCurrentEffect(Device device, EffectsNameRequest effectName);

    Flux<String> getEffects(Device device);

    Mono<Effect> createEffect(Device device, WriteRequest effect);

}
