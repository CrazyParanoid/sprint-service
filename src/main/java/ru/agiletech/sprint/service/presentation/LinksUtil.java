package ru.agiletech.sprint.service.presentation;

import lombok.experimental.UtilityClass;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import ru.agiletech.sprint.service.application.SprintDTO;

@UtilityClass
public class LinksUtil {

    void addLinks(SprintDTO sprintDTO){
        addSelfLink(sprintDTO);
        addAllSprintsLink(sprintDTO);
        addCompleteLink(sprintDTO);
        addScheduleLink(sprintDTO);
        addStartLink(sprintDTO);
    }

    private void addSelfLink(SprintDTO sprintDTO){
       sprintDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SprintResource.class)
               .getSprint(sprintDTO.getId()))
               .withSelfRel());
    }

    private void addStartLink(SprintDTO sprintDTO){
        sprintDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SprintResource.class)
                .startSprint(sprintDTO.getId(), null, null))
                .withRel("start"));
    }

    private void addScheduleLink(SprintDTO sprintDTO){
        sprintDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SprintResource.class)
                .scheduleSprint(sprintDTO.getId(), null))
                .withRel("schedule"));
    }

    private void addCompleteLink(SprintDTO sprintDTO){
        sprintDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SprintResource.class)
                .completeSprint(sprintDTO.getId()))
                .withRel("complete"));
    }

    private void addAllSprintsLink(SprintDTO sprintDTO){
        sprintDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SprintResource.class)
                .getSprints())
                .withRel("sprints"));
    }

}
