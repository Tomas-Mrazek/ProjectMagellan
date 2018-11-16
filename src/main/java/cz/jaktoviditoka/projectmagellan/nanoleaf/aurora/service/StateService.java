package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;

public interface StateService {

    OnResponse isOn(Device device);

    void setOn(Device device, OnRequest on);

    BrightnessResponse getBrightness(Device device);

    void setBrightness(Device device, BrightnessRequest brightness);

    HueResponse getHue(Device device);

    void setHue(Device device, HueRequest hue);

    SaturationResponse getSaturation(Device device);

    void setSaturation(Device device, SaturationRequest saturation);

    ColorTemperatureResponse getColorTemperature(Device device);

    void setColorTemperature(Device device, ColorTemperatureRequest colorTemperature);

    ColorMode getColorMode(Device device);

}
