package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.info;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.state.StateResponse;
import lombok.Data;

@Data
public class ControllerInfoResponse {

    private String name;
    private String serialNo;
    private String manufacturer;
    private String firmwareVersion;
    private String model;
    private StateResponse state;

}
