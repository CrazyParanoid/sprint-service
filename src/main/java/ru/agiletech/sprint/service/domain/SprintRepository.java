package ru.agiletech.sprint.service.domain;

import java.util.Set;

public interface SprintRepository {

    void save(Sprint sprint);

    Sprint sprintOfId(SprintId sprintId);

    Set<Sprint> allSprints();

}
