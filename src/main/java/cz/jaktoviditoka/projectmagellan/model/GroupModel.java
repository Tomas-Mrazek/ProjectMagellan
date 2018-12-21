package cz.jaktoviditoka.projectmagellan.model;

import cz.jaktoviditoka.projectmagellan.domain.Group;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class GroupModel {

    Set<Group> groups;

    public boolean deleteGroup(Group group) {
        return groups.remove(group);
    }

    public void renameGroup(Group group) {
        groups.stream()
            .filter(element -> {
                return element.getUuid().equals(group.getUuid());
            })
            .findFirst()
            .ifPresent(element -> {
                element.setName(group.getName());
            });
    }

}
