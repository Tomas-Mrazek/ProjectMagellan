package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.auth.Authorization;

public interface AuthorizationService {

    Authorization addUser(Device device);

    void deleteUser(Device device);

}
