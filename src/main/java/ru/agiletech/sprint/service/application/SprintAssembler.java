package ru.agiletech.sprint.service.application;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.agiletech.sprint.service.domain.Sprint;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SprintAssembler {

    private final ModelMapper modelMapper;

    SprintDTO writeDTO(Sprint sprint){
        var sprintDTO = modelMapper.map(sprint, SprintDTO.class);

        Set<String> tasks = sprint.tasks();
        var status = sprint.status();
        var id = sprint.sprintId();

        sprintDTO.setId(id);
        sprintDTO.setStatus(status);
        sprintDTO.setTasks(tasks);
        sprintDTO.setSprintDays(sprint.daysOfSprint());
        sprintDTO.setStartDate(sprint.startDateOfSprint());
        sprintDTO.setEndDate(sprint.endDateOfSprint());

        return sprintDTO;
    }

}
