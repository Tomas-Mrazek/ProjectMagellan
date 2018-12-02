package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.Effect;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectName;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectNameList;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectNameRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effect.EffectRequest;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.EffectsService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;

@Service
public class EffectsServiceImpl implements EffectsService {

    private static final String BASE_URL = "/api/v1/";
    private static final String EFFECTS_NAME = "/effects/select";
    private static final String EFFECTS_LIST = "/effects/effectsList";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public EffectName getCurrentEffect(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + EFFECTS_NAME);
        ResponseEntity<EffectName> response = restTemplate.getForEntity(uri, EffectName.class);
        return response.getBody();
    }

    @Override
    public void setCurrentEffect(Device device, EffectNameRequest effectName) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + EFFECTS_NAME);
        restTemplate.put(uri, effectName);
    }

    @Override
    public EffectNameList getEffects(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + EFFECTS_LIST);
        ResponseEntity<EffectNameList> response = restTemplate.getForEntity(uri, EffectNameList.class);
        return response.getBody();
    }

    @Override
    public Effect createEffect(Device device, EffectRequest effect) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + EFFECTS_NAME);
        ResponseEntity<Effect> response = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<EffectRequest>(effect), Effect.class);
        return response.getBody();
    }

}
