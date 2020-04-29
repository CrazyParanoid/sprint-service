package ru.agiletech.sprint.service.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.agiletech.sprint.service.application.SprintDTO;
import ru.agiletech.sprint.service.application.SprintService;
import ru.agiletech.sprint.service.presentation.hateoas.LinksUtil;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(value = "REST-ресурс модели спринта")
public class SprintResource {

    private final SprintService sprintService;

    @PostMapping(value = "/sprints")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Создать спринт")
    public SprintDTO createSprint(@RequestBody @Valid SprintDTO sprintDTO){
        var createdSprint = sprintService.createSprint(sprintDTO);
        LinksUtil.addLinks(createdSprint);

        return createdSprint;
    }

    @GetMapping(value = "/sprints/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Найти спринт по идентификатору")
    public SprintDTO getSprint(@PathVariable String id){
        var sprint = sprintService.searchSprintById(id);
        LinksUtil.addLinks(sprint);

        return sprint;
    }

    @PutMapping(value = "/sprints/{id}/start")
    @ApiOperation(value = "Начать спринт")
    public ResponseEntity<Void> startSprint(@PathVariable String       id,
                                            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                    LocalDate    startDate,
                                            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                    LocalDate    endDate){
        sprintService.startSprint(startDate,
                endDate,
                id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/sprints/{id}/tasks")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Спланировать спринт - добавить задачу")
    public ResponseEntity<Void> scheduleSprint(@PathVariable(name = "id") String  sprintId,
                                               @RequestParam              String  taskId){
        sprintService.scheduleSprint(sprintId, taskId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/sprints/{id}/complete")
    @ApiOperation(value = "Завершить спринт")
    public ResponseEntity<Void> completeSprint(@PathVariable String id){
        sprintService.completeSprint(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/sprints")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Найти все спринты")
    public Set<SprintDTO> getSprints(){
        Set<SprintDTO> sprints = sprintService.searchAllSprints();

        if(CollectionUtils.isNotEmpty(sprints))
            sprints.forEach(LinksUtil::addLinks);

        return sprints;
    }

}
