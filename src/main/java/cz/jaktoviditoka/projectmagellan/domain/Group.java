package cz.jaktoviditoka.projectmagellan.domain;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class Group {

    UUID uuid;
    String name;
    Set<BaseDevice> devices;
    //TODO Set of Effects;

}
