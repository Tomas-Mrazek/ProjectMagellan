package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import reactor.core.publisher.Mono;

public interface StateService {

    Mono<OnResponse> isOn(Device device);

    Mono<Void> setOn(Device device, OnRequest on);

    Mono<BrightnessResponse> getBrightness(Device device);

    Mono<Void> setBrightness(Device device, BrightnessRequest brightness);

    Mono<HueResponse> getHue(Device device);

    Mono<Void> setHue(Device device, HueRequest hue);

    Mono<SaturationResponse> getSaturation(Device device);

    Mono<Void> setSaturation(Device device, SaturationRequest saturation);

    Mono<ColorTemperatureResponse> getColorTemperature(Device device);

    Mono<Void> setColorTemperature(Device device, ColorTemperatureRequest colorTemperature);

    Mono<ColorMode> getColorMode(Device device);

}
