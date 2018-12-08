package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.effects.EffectsResponse;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.layout.PanelLayoutResponse;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.rhythm.RhythmResponse;
import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.state.StateResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class InfoResponse {

    String name;
    String serialNo;
    String manufacturer;
    String firmwareVersion;
    String model;
    StateResponse state;
    EffectsResponse effects;
    PanelLayoutResponse panelLayout;
    RhythmResponse rhythm;

}
