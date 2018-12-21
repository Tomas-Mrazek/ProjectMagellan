package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import reactor.core.publisher.Mono;

public interface StateService {

    Mono<OnResponse> isOn(NanoleafAuroraDevice device);

    Mono<Void> setOn(NanoleafAuroraDevice device, OnRequest on);

    Mono<BrightnessResponse> getBrightness(NanoleafAuroraDevice device);

    Mono<Void> setBrightness(NanoleafAuroraDevice device, BrightnessRequest brightness);

    Mono<HueResponse> getHue(NanoleafAuroraDevice device);

    Mono<Void> setHue(NanoleafAuroraDevice device, HueRequest hue);

    Mono<SaturationResponse> getSaturation(NanoleafAuroraDevice device);

    Mono<Void> setSaturation(NanoleafAuroraDevice device, SaturationRequest saturation);

    Mono<ColorTemperatureResponse> getColorTemperature(NanoleafAuroraDevice device);

    Mono<Void> setColorTemperature(NanoleafAuroraDevice device, ColorTemperatureRequest colorTemperature);

    Mono<ColorMode> getColorMode(NanoleafAuroraDevice device);

}
