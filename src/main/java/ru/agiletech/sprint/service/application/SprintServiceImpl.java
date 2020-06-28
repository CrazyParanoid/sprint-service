package ru.agiletech.sprint.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agiletech.sprint.service.application.dto.SprintDTO;
import ru.agiletech.sprint.service.domain.*;
import ru.agiletech.sprint.service.domain.task.TaskId;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements SprintService{

    private final DomainEventPublisher<SprintScheduled> sprintScheduledEventPublisher;
    private final SprintRepository sprintRepository;
    private final SprintAssembler sprintAssembler;

    @Override
    @Transactional
    public SprintDTO createSprint(SprintDTO sprintDTO) {
        log.info("Create sprint");
        Sprint sprint = Sprint.create(sprintDTO.getName(),
                sprintDTO.getGoal(),
                sprintDTO.getProjectKey());

        String id = sprint.sprintId();
        log.info("Sprint with id {} has been created", id);
        sprintRepository.save(sprint);

        log.info("Sprint with id {} has been saved", id);
        return sprintAssembler.writeDTO(sprint);
    }

    @Override
    @Transactional
    public void startSprint(String rawSprintId, LocalDate startDate, LocalDate endDate) {
        log.info("Start sprint with id{}", rawSprintId);
        SprintId sprintId = SprintId.identifySprint(rawSprintId);
        Sprint sprint = sprintRepository.sprintOfId(sprintId);

        sprint.start(startDate, endDate);
        log.info("Sprint with id{} has been started", rawSprintId);

        sprintRepository.save(sprint);
        log.info("Sprint with id {} has been saved", rawSprintId);
    }

    @Override
    @Transactional
    public void completeSprint(String id) {
        log.info("Complete sprint with id{}", id);
        SprintId sprintId = SprintId.identifySprint(id);
        Sprint sprint = sprintRepository.sprintOfId(sprintId);

        sprint.complete();
        log.info("Sprint with id{} has been completed", id);

        sprintRepository.save(sprint);
    }

    @Override
    @Transactional
    public void scheduleSprint(String rawSprintId, String rawTaskId) {
        log.info("Schedule sprint with id{}", rawSprintId);
        SprintId sprintId = SprintId.identifySprint(rawSprintId);
        Sprint sprint = sprintRepository.sprintOfId(sprintId);

        SprintScheduled event = sprint.schedule(rawTaskId);
        sprintScheduledEventPublisher.publish(Collections.singletonList(event));

        log.info("Sprint with id{} has been scheduled", sprint.sprintId());
        sprintRepository.save(sprint);
    }

    @Override
    public SprintDTO searchSprintById(String id) {
        log.info("Search sprint with id{}", id);

        SprintId sprintId = SprintId.identifySprint(id);
        Sprint sprint = sprintRepository.sprintOfId(sprintId);
        log.info("Sprint with id{} has been found", id);

        return sprintAssembler.writeDTO(sprint);
    }

    @Override
    public Set<SprintDTO> searchAllSprints() {
        log.info("Search all created sprints");

        Set<Sprint> sprints = sprintRepository.allSprints();
        log.info("All created sprints has been found");

        Set<SprintDTO> sprintDTOS = new HashSet<>();
        if(CollectionUtils.isNotEmpty(sprints))
            sprints.forEach(sprint -> sprintDTOS.add(sprintAssembler.writeDTO(sprint)));

        return sprintDTOS;
    }

}
