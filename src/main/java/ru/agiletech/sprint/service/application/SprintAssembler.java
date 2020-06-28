package ru.agiletech.sprint.service.application;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.agiletech.sprint.service.application.dto.SprintDTO;
import ru.agiletech.sprint.service.domain.Sprint;
import ru.agiletech.sprint.service.domain.SprintSnapshot;


@Service
@RequiredArgsConstructor
public class SprintAssembler {

    private final ModelMapper modelMapper;

    SprintDTO writeDTO(Sprint sprint){
        var sprintDTO = modelMapper.map(sprint, SprintDTO.class);
        var id = sprint.sprintId();

        SprintSnapshot snapshot = sprint.makeSnapshot();

        sprintDTO.setId(id);
        sprintDTO.setStatus(snapshot.getStatus());
        sprintDTO.setTasks(snapshot.getTasks());
        sprintDTO.setSprintDays(snapshot.daysOfSprint());
        sprintDTO.setStartDate(snapshot.getStartDateOfSprint());
        sprintDTO.setEndDate(snapshot.getEndDateOfSprint());

        return sprintDTO;
    }

}
