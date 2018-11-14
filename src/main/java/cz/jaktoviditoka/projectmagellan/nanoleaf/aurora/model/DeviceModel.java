package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

import javax.annotation.PostConstruct;

import cz.jaktoviditoka.projectmagellan.device.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.auth.Authorization;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.AuthorizationService;
import cz.jaktoviditoka.projectmagellan.settings.AppSettings;
import cz.jaktoviditoka.projectmagellan.ssdp.SSDPClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class DeviceModel {

    @Getter
    Set<Device> devices;

    AppSettings appSettings;
    SSDPClient ssdpclient;
    AuthorizationService authService;

    @Autowired
    public DeviceModel(AppSettings appSettings, SSDPClient ssdpclient, AuthorizationService authService) {
        this.appSettings = appSettings;
        this.ssdpclient = ssdpclient;
        this.authService = authService;
    }

    @PostConstruct
    private void init() {
        devices = appSettings.getDevices();
    }

    public Set<Device> discover() throws IOException {
        Set<Device> newDevices = ssdpclient.mSearch();
        newDevices.removeAll(devices);
        return newDevices;
    }

    public void pair(Device device) {
        if (!device.getAuthToken().isEmpty()) {
            log.warn("Device is already paired.");
        }
        Authorization auth = authService.addUser(device);
        device.setAuthToken(auth.getAuthToken());
        appSettings.getDevices().add(device);
        try {
            appSettings.saveSettings();
        } catch (IOException e) {
            log.error("Failed to save paired device to settings.", e);
        }
    }

    public void unpair(Device device) {
        authService.deleteUser(device);
        appSettings.getDevices().remove(device);
        try {
            appSettings.saveSettings();
        } catch (IOException e) {
            log.error("Failed to save removed device to settings.", e);
        }
    }

}
