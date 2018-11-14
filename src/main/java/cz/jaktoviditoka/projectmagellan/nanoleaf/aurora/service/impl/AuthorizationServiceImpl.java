package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import cz.jaktoviditoka.projectmagellan.device.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.auth.Authorization;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final String BASE_URL = "/api/v1/";
    private static final String NEW = "new";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Authorization addUser(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + NEW)
            .build();
        ResponseEntity<Authorization> response = restTemplate.getForEntity(uriComponents.toUri(),
                Authorization.class);
        return response.getBody();
    }

    @Override
    public void deleteUser(Device device) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(device.getIp().getHostAddress())
            .port(device.getPort())
            .path(BASE_URL + device.getAuthToken())
            .build();
        restTemplate.delete(uriComponents.toUri());
    }

}
