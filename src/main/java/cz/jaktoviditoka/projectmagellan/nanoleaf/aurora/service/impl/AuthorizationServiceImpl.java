package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.auth.Authorization;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.AuthorizationService;
import cz.jaktoviditoka.projectmagellan.utils.UriHelper;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final String BASE_URL = "/api/v1/";
    private static final String NEW = "new";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Authorization addUser(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + NEW);
        ResponseEntity<Authorization> response = restTemplate.postForEntity(uri, null,
                Authorization.class);
        return response.getBody();
    }

    @Override
    public void deleteUser(Device device) {
        URI uri = UriHelper.getUri(device.getIp().getHostAddress(), device.getPort(),
                BASE_URL + device.getAuthToken());
        restTemplate.delete(uri);
    }

}
