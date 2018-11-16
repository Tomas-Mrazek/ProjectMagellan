package cz.jaktoviditoka.projectmagellan.settings;

import java.util.Set;

import cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.domain.Device;
import lombok.Data;

@Data
public class Settings {

    private Set<Device> devices;

}
