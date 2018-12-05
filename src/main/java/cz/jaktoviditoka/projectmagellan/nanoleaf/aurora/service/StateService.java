package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import reactor.core.publisher.Mono;

public interface StateService {

    OnResponse isOn(Device device);

    Mono<Void> setOn(Device device, OnRequest on);

    BrightnessResponse getBrightness(Device device);

    Mono<Void> setBrightness(Device device, BrightnessRequest brightness);

    HueResponse getHue(Device device);

    void setHue(Device device, HueRequest hue);

    SaturationResponse getSaturation(Device device);

    void setSaturation(Device device, SaturationRequest saturation);

    ColorTemperatureResponse getColorTemperature(Device device);

    Mono<Void> setColorTemperature(Device device, ColorTemperatureRequest colorTemperature);

    ColorMode getColorMode(Device device);

}
