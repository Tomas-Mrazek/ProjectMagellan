package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import cz.jaktoviditoka.projectmagellan.device.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;

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

    public StateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public OnResponse isOn(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + ON)
            .build();
        ResponseEntity<OnResponse> response = restTemplate.getForEntity(uriComponents.toUri(),
                OnResponse.class);
        return response.getBody();
    }

    @Override
    public void setOn(Device device, OnRequest on) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + STATE)
            .build();
        restTemplate.put(uriComponents.toUri(), on);
    }

    @Override
    public BrightnessResponse getBrightness(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + BRIGHTNESS)
            .build();
        ResponseEntity<BrightnessResponse> response = restTemplate.getForEntity(uriComponents.toUri(),
                BrightnessResponse.class);
        return response.getBody();
    }

    @Override
    public void setBrightness(Device device, Brightness brightness) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + STATE)
            .build();
        restTemplate.put(uriComponents.toUri(), brightness);
    }

    @Override
    public HueResponse getHue(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + HUE)
            .build();
        ResponseEntity<HueResponse> response = restTemplate.getForEntity(uriComponents.toUri(),
                HueResponse.class);
        return response.getBody();
    }

    @Override
    public void setHue(Device device, Hue hue) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + STATE)
            .build();
        restTemplate.put(uriComponents.toUri(), hue);
    }

    @Override
    public SaturationResponse getSaturation(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + SATURATION)
            .build();
        ResponseEntity<SaturationResponse> response = restTemplate.getForEntity(uriComponents.toUri(),
                SaturationResponse.class);
        return response.getBody();
    }

    @Override
    public void setSaturation(Device device, Saturation saturation) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + STATE)
            .build();
        restTemplate.put(uriComponents.toUri(), saturation);
    }

    @Override
    public ColorTemperatureResponse getColorTemperature(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + COLOR_TEMPERATURE)
            .build();
        ResponseEntity<ColorTemperatureResponse> response = restTemplate.getForEntity(uriComponents.toUri(),
                ColorTemperatureResponse.class);
        return response.getBody();
    }

    @Override
    public void setColorTemperature(Device device, ColorTemperature colorTemperature) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + STATE)
            .build();
        restTemplate.put(uriComponents.toUri(), colorTemperature);
    }

    @Override
    public ColorMode getColorMode(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken() + COLOR_MODE)
            .build();
        ResponseEntity<ColorMode> response = restTemplate.getForEntity(uriComponents.toUri(),
                ColorMode.class);
        return response.getBody();
    }

}
