package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.service;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.model.Authorization;

public interface AuthorizationService {

    Authorization addUser();

    void deleteUser();

}
