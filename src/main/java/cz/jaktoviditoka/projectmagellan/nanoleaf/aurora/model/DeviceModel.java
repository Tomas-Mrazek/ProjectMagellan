package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model;

import cz.jaktoviditoka.projectmagellan.domain.BaseDeviceType;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.InfoResponse;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.auth.Authorization;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.*;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.AuthorizationService;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.InfoService;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.StateService;
import cz.jaktoviditoka.projectmagellan.settings.AppSettings;
import cz.jaktoviditoka.projectmagellan.ssdp.SSDPClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    InfoService infoService;
    StateService stateService;

    @Autowired
    public DeviceModel(AppSettings appSettings, SSDPClient ssdpclient,
            AuthorizationService authService, InfoService infoService, StateService stateService) {
        this.appSettings = appSettings;
        this.ssdpclient = ssdpclient;
        this.authService = authService;
        this.infoService = infoService;
        this.stateService = stateService;
    }

    @PostConstruct
    public void init() {
        devices = appSettings.getDevices();
    }

    public void discover(Set<Device> devices) throws IOException {
        ssdpclient.mSearch(BaseDeviceType.NANOLEAF_AURORA, devices);
    }

    public Mono<Boolean> pair(Device device) {
        if (StringUtils.isNotEmpty(device.getAuthToken())) {
            log.warn("Device is already paired.");
            return Mono.just(false).log();
        }
        return authService.addUser(device)
            .map(Authorization::getAuthToken)
            .log()
            .filter(StringUtils::isNotEmpty)
            .log()
            .switchIfEmpty(Mono.error(new IllegalStateException("Failed to pair device - auth token does not exist.")))
            .doOnSuccess(consumer -> {
                device.setAuthToken(consumer);
            })
            .doOnError(consumer -> {
                log.warn("Error inside DeviceModel.", consumer);
            })
            .flatMap(mapper -> {
                appSettings.getDevices().add(device);
                try {
                    appSettings.saveSettings();
                } catch (IOException e) {
                    log.error("Failed to save paired device.", e);
                    return Mono.error(e);
                }
                return Mono.just(true).log();
            });
    }

    public Mono<Boolean> unpair(Device device) {
        authService.deleteUser(device).block();

        appSettings.getDevices().remove(device);
        try {
            appSettings.saveSettings();
        } catch (IOException e) {
            log.error("Failed to save removed device to settings.", e);
        }

        return Mono.just(true);
    }

    public Mono<InfoResponse> getInfo(Device device) {
        return infoService.getInfo(device)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<OnResponse> isOn(Device device) {
        return stateService.isOn(device)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<Void> setOn(Device device, boolean power) {
        On on = new On(power);
        OnRequest request = new OnRequest(on);
        return stateService.setOn(device, request)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<BrightnessResponse> getBrightness(Device device) {
        return stateService.getBrightness(device)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<Void> setBrightness(Device device, Number brightness) {
        BrightnessValue brightnessValue = new BrightnessValue();
        brightnessValue.setValue(brightness.intValue());
        BrightnessRequest request = new BrightnessRequest(brightnessValue);
        return stateService.setBrightness(device, request)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<HueResponse> getHue(Device device) {
        return stateService.getHue(device)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<Void> setHue(Device device, Number hue) {
        HueValue hueValue = new HueValue();
        hueValue.setValue(hue.intValue());
        HueRequest request = new HueRequest(hueValue);
        return stateService.setHue(device, request)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<SaturationResponse> getSaturation(Device device) {
        return stateService.getSaturation(device)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<Void> setSaturation(Device device, Number saturation) {
        SaturationValue saturationValue = new SaturationValue();
        saturationValue.setValue(saturation.intValue());
        SaturationRequest request = new SaturationRequest(saturationValue);
        return stateService.setSaturation(device, request)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<ColorTemperatureResponse> getColorTemperature(Device device) {
        return stateService.getColorTemperature(device)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<Void> setColorTemperature(Device device, Number colorTemperature) {
        ColorTemperatureValue colorTemperatureValue = new ColorTemperatureValue();
        colorTemperatureValue.setValue(colorTemperature.intValue());
        ColorTemperatureRequest colorTemperatureRequest = new ColorTemperatureRequest(colorTemperatureValue);
        return stateService.setColorTemperature(device, colorTemperatureRequest)
            .subscribeOn(Schedulers.elastic());
    }

    public Mono<ColorMode> getColorMode(Device device) {
        return stateService.getColorMode(device)
            .subscribeOn(Schedulers.elastic());
    }

}
