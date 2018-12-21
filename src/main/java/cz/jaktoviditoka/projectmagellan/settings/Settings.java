package cz.jaktoviditoka.projectmagellan.settings;

import java.util.Set;

import cz.jaktoviditoka.projectmagellan.domain.NanoleafAuroraDevice;
import lombok.Data;

@Data
public class Settings {

    private Set<NanoleafAuroraDevice> devices;

}
