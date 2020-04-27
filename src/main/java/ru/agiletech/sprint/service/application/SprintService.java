package ru.agiletech.sprint.service.application;

import java.time.LocalDate;
import java.util.Set;

public interface SprintService {

    SprintDTO createSprint(SprintDTO sprintDTO);

    void startSprint(LocalDate startDate,
                     LocalDate endDate,
                     String    id);

    void completeSprint(String  id);

    void scheduleSprint(String id,
                        String rawTaskId);

    SprintDTO searchSprintById(String id);

    Set<SprintDTO> searchAllSprints();

}
