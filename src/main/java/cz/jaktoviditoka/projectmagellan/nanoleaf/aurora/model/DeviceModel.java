package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model;

import cz.jaktoviditoka.projectmagellan.domain.BaseDeviceType;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.auth.Authorization;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.exception.NotAuthorizedExxception;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.AuthorizationService;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import cz.jaktoviditoka.projectmagellan.settings.AppSettings;
import cz.jaktoviditoka.projectmagellan.ssdp.SSDPClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Set;

import javax.annotation.PostConstruct;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class DeviceModel {

    @Getter
    Set<Device> devices;

    AppSettings appSettings;
    SSDPClient ssdpclient;
    AuthorizationService authService;
    StateService stateService;

    @Autowired
    public DeviceModel(AppSettings appSettings, SSDPClient ssdpclient, AuthorizationService authService,
            StateService stateService) {
        this.appSettings = appSettings;
        this.ssdpclient = ssdpclient;
        this.authService = authService;
        this.stateService = stateService;
    }

    @PostConstruct
    public void init() {
        devices = appSettings.getDevices();
    }

    public void discover(Set<Device> devices) throws IOException {
        ssdpclient.mSearch(BaseDeviceType.NANOLEAF_AURORA, devices);
    }

    public boolean pair(Device device) throws NotAuthorizedExxception {
        if (StringUtils.isNotEmpty(device.getAuthToken())) {
            log.warn("Device is already paired.");
            return false;
        }
        Authorization auth = new Authorization();
        try {
            auth = authService.addUser(device);
        } catch (HttpStatusCodeException e) {
            log.warn("Failed to pair device.", e);
            if (e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                throw new NotAuthorizedExxception();
            }
            return false;
        }
        if (StringUtils.isEmpty(auth.getAuthToken())) {
            log.warn("Failed to pair device – auth token does not exist.");
            return false;
        }
        device.setAuthToken(auth.getAuthToken());

        appSettings.getDevices().add(device);
        try {
            appSettings.saveSettings();
        } catch (IOException e) {
            log.error("Failed to save paired device.", e);
        }

        return true;
    }

    public boolean unpair(Device device) {
        try {
            authService.deleteUser(device);
        } catch (HttpStatusCodeException e) {
            log.warn("Failed to unpair device.", e);
            return false;
        }

        appSettings.getDevices().remove(device);
        try {
            appSettings.saveSettings();
        } catch (IOException e) {
            log.error("Failed to save removed device to settings.", e);
        }

        return true;
    }


    public OnResponse isOn(Device device) {
        return stateService.isOn(device);
    }

    public void setOn(Device device, boolean power) {
        On on = new On(power);
        OnRequest onRequest = new OnRequest(on);
        Mono<Void> response = stateService.setOn(device, onRequest);
        response.subscribe();
    }

    public BrightnessResponse getBrightness(Device device) {
        return stateService.getBrightness(device);
    }

    public void setBrightness(Device device, Number brightness) {
        BrightnessValue brightnessValue = new BrightnessValue();
        brightnessValue.setValue(brightness.intValue());
        BrightnessRequest brightnessRequest = new BrightnessRequest(brightnessValue);
        Mono<Void> response = stateService.setBrightness(device, brightnessRequest);
        response.subscribe();
    }

    public ColorTemperatureResponse getColorTemperature(Device device) {
        return stateService.getColorTemperature(device);
    }

    public void setColorTemperature(Device device, Number colorTemperature) {
        ColorTemperatureValue colorTemperatureValue = new ColorTemperatureValue();
        colorTemperatureValue.setValue(colorTemperature.intValue());
        ColorTemperatureRequest colorTemperatureRequest = new ColorTemperatureRequest(colorTemperatureValue);
        Mono<Void> response = stateService.setColorTemperature(device, colorTemperatureRequest);
        response.subscribe();
    }

}
