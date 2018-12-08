package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
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

    @Override
    public Mono<OnResponse> isOn(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + ON);
        return WebClient.create()
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToMono(OnResponse.class);
    }

    @Override
    public Mono<Void> setOn(Device device, OnRequest on) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        return WebClient.create()
            .method(HttpMethod.PUT)
            .uri(uri)
            .body(BodyInserters.fromObject(on))
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public Mono<BrightnessResponse> getBrightness(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + BRIGHTNESS);
        return WebClient.create()
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToMono(BrightnessResponse.class);
    }

    @Override
    public Mono<Void> setBrightness(Device device, BrightnessRequest brightness) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        return WebClient.create()
            .method(HttpMethod.PUT)
            .uri(uri)
            .body(BodyInserters.fromObject(brightness))
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public Mono<HueResponse> getHue(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + HUE);
        return WebClient.create()
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToMono(HueResponse.class);
    }

    @Override
    public Mono<Void> setHue(Device device, HueRequest hue) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        return WebClient.create()
            .method(HttpMethod.PUT)
            .uri(uri)
            .body(BodyInserters.fromObject(hue))
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public Mono<SaturationResponse> getSaturation(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + SATURATION);
        return WebClient.create()
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToMono(SaturationResponse.class);
    }

    @Override
    public Mono<Void> setSaturation(Device device, SaturationRequest saturation) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        return WebClient.create()
            .method(HttpMethod.PUT)
            .uri(uri)
            .body(BodyInserters.fromObject(saturation))
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public Mono<ColorTemperatureResponse> getColorTemperature(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + COLOR_TEMPERATURE);
        return WebClient.create()
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToMono(ColorTemperatureResponse.class);
    }

    @Override
    public Mono<Void> setColorTemperature(Device device, ColorTemperatureRequest colorTemperature) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + STATE);
        return WebClient.create()
            .method(HttpMethod.PUT)
            .uri(uri)
            .body(BodyInserters.fromObject(colorTemperature))
            .retrieve()
            .bodyToMono(Void.class);
    }

    @Override
    public Mono<ColorMode> getColorMode(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken() + COLOR_MODE);
        return WebClient.create()
            .method(HttpMethod.GET)
            .uri(uri)
            .retrieve()
            .bodyToMono(ColorMode.class);
    }

}
