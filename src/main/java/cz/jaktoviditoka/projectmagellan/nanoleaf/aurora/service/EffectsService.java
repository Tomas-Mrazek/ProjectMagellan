package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import java.util.List;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.effect.Effect;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.effect.EffectName;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.effect.EffectNameRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.effect.EffectRequest;

public interface EffectsService {

    EffectName getCurrentEffect();

    void setCurrentEffect(EffectNameRequest effectName);

    List<EffectName> getEffects();

    Effect createEffect(EffectRequest effect);

}
