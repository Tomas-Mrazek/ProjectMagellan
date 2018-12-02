package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.Effect;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectName;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectNameList;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectNameRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectRequest;

public interface EffectsService {

    EffectName getCurrentEffect(Device device);

    void setCurrentEffect(Device device, EffectNameRequest effectName);

    EffectNameList getEffects(Device device);

    Effect createEffect(Device device, EffectRequest effect);

}
