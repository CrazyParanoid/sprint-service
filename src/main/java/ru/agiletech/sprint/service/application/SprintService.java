package ru.agiletech.sprint.service.application;

import ru.agiletech.sprint.service.application.dto.SprintDTO;

import java.time.LocalDate;
import java.util.Set;

public interface SprintService {

    SprintDTO createSprint(SprintDTO sprintDTO);

    void startSprint(String rawSprintId, LocalDate startDate, LocalDate endDate);

    void completeSprint(String rawSprintId);

    void scheduleSprint(String rawSprintId, String rawTaskId);

    SprintDTO searchSprintById(String rawSprintId);

    Set<SprintDTO> searchAllSprints();

}
