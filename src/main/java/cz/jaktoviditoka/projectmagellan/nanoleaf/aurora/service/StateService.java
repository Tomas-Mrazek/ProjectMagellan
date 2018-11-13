package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.device.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.*;

public interface StateService {

    OnResponse isOn(Device device);

    void setOn(Device device, OnRequest on);

    BrightnessResponse getBrightness(Device device);

    void setBrightness(Device device, Brightness brightness);

    HueResponse getHue(Device device);

    void setHue(Device device, Hue hue);

    SaturationResponse getSaturation(Device device);

    void setSaturation(Device device, Saturation saturation);

    ColorTemperatureResponse getColorTemperature(Device device);

    void setColorTemperature(Device device, ColorTemperature colorTemperature);

    ColorMode getColorMode(Device device);

}
