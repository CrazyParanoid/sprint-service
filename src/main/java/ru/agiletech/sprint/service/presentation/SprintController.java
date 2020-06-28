package ru.agiletech.sprint.service.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.agiletech.sprint.service.application.dto.ScheduleSprintCommand;
import ru.agiletech.sprint.service.application.dto.SprintDTO;
import ru.agiletech.sprint.service.application.SprintService;
import ru.agiletech.sprint.service.application.dto.StartSprintCommand;
import ru.agiletech.sprint.service.presentation.hateoas.LinksUtil;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
@Api(value = "REST-ресурс модели спринта")
public class SprintController {

    private final SprintService sprintService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Создать спринт")
    public SprintDTO createSprint(@RequestBody @Valid SprintDTO sprintDTO){
        var createdSprint = sprintService.createSprint(sprintDTO);
        LinksUtil.addLinks(createdSprint);

        return createdSprint;
    }

    @PatchMapping(value = "/{id}/start")
    @ApiOperation(value = "Начать спринт")
    public ResponseEntity<Void> startSprint(@PathVariable String id,
                                            @RequestBody @Valid StartSprintCommand command){
        sprintService.startSprint(id, command.getStartDate(), command.getEndDate());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{id}/tasks")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Спланировать спринт - добавить задачу")
    public ResponseEntity<Void> scheduleSprint(@PathVariable(name = "id") String sprintId,
                                               @RequestBody @Valid ScheduleSprintCommand command){
        sprintService.scheduleSprint(sprintId, command.getTaskId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}/complete")
    @ApiOperation(value = "Завершить спринт")
    public ResponseEntity<Void> completeSprint(@PathVariable String id){
        sprintService.completeSprint(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Найти спринт по идентификатору")
    public SprintDTO getSprint(@PathVariable String id){
        var sprint = sprintService.searchSprintById(id);
        LinksUtil.addLinks(sprint);

        return sprint;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Найти все спринты")
    public Set<SprintDTO> getSprints(){
        Set<SprintDTO> sprints = sprintService.searchAllSprints();
        if(CollectionUtils.isNotEmpty(sprints))
            sprints.forEach(LinksUtil::addLinks);

        return sprints;
    }

}
