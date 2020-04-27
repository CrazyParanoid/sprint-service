package ru.agiletech.sprint.service.domain;

import lombok.Getter;

import java.util.Date;

@Getter
public class SprintStarted extends DomainEvent{

    private SprintId sprintId;

    SprintStarted(Date        occurredOn,
                  String      name,
                  SprintId    sprintId) {
        super(occurredOn, name);
        this.sprintId = sprintId;
    }

}
