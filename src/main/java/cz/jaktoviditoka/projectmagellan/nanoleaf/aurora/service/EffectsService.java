package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.Effect;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.EffectsNameRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.WriteRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EffectsService {

    Mono<String> getCurrentEffect(NanoleafAuroraDevice device);

    Mono<Void> setCurrentEffect(NanoleafAuroraDevice device, EffectsNameRequest effectName);

    Flux<String> getEffects(NanoleafAuroraDevice device);

    Mono<Effect> createEffect(NanoleafAuroraDevice device, WriteRequest effect);

}
