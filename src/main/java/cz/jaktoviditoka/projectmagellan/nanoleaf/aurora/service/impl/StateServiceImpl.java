package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Service
public class StateServiceImpl implements StateService {

    private static final String BASE_URL = "/api/v1/";
    private static final String STATE = "/state";
    private static final String ON = "/state/on";
    private static final String BRIGHTNESS = "/state/brightness";
    private static final String HUE = "/state/hue";
    private static final String SATURATION = "/state/sat";
    private static final String COLOR_TEMPERATURE = "/state/ct";
    private static final String COLOR_MODE = "/state/colorMode";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient client;

    public StateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public OnResponse isOn(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + ON);
        ResponseEntity<OnResponse> response = restTemplate.getForEntity(uri, OnResponse.class);
        return response.getBody();
    }

    @Override
    public Mono<Void> setOn(Device device, OnRequest on) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        return client
            .method(HttpMethod.PUT)
            .uri(uri)
            .body(BodyInserters.fromObject(on))
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public BrightnessResponse getBrightness(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + BRIGHTNESS);
        ResponseEntity<BrightnessResponse> response = restTemplate.getForEntity(uri,
                BrightnessResponse.class);
        return response.getBody();
    }

    @Override
    public Mono<Void> setBrightness(Device device, BrightnessRequest brightness) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        return client
            .method(HttpMethod.PUT)
            .uri(uri)
            .body(BodyInserters.fromObject(brightness))
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public HueResponse getHue(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + HUE);
        ResponseEntity<HueResponse> response = restTemplate.getForEntity(uri, HueResponse.class);
        return response.getBody();
    }

    @Override
    public void setHue(Device device, HueRequest hue) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        restTemplate.put(uri, hue);
    }

    @Override
    public SaturationResponse getSaturation(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + SATURATION);
        ResponseEntity<SaturationResponse> response = restTemplate.getForEntity(uri, SaturationResponse.class);
        return response.getBody();
    }

    @Override
    public void setSaturation(Device device, SaturationRequest saturation) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        restTemplate.put(uri, saturation);
    }

    @Override
    public ColorTemperatureResponse getColorTemperature(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + COLOR_TEMPERATURE);
        ResponseEntity<ColorTemperatureResponse> response = restTemplate.getForEntity(uri,
                ColorTemperatureResponse.class);
        return response.getBody();
    }

    @Override
    public Mono<Void> setColorTemperature(Device device, ColorTemperatureRequest colorTemperature) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        return client
            .method(HttpMethod.PUT)
            .uri(uri)
            .body(BodyInserters.fromObject(colorTemperature))
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public ColorMode getColorMode(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + COLOR_MODE);
        ResponseEntity<ColorMode> response = restTemplate.getForEntity(uri, ColorMode.class);
        return response.getBody();
    }

}
