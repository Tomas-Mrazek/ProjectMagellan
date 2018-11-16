package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.Effect;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectName;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectNameRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.EffectsService;

@Service
public class EffectsServiceImpl implements EffectsService {

    private static final String BASE_URL = "/api/v1/";
    private static final String EFFECTS_NAME = "/effects/select";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public EffectName getCurrentEffect(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + EFFECTS_NAME)
            .build();
        ResponseEntity<EffectName> response = restTemplate.getForEntity(uriComponents.toUri(),
                EffectName.class);
        return response.getBody();
    }

    @Override
    public void setCurrentEffect(Device device, EffectNameRequest effectName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<EffectName> getEffects(Device device) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Effect createEffect(Device device, EffectRequest effect) {
        throw new UnsupportedOperationException();
    }

}
