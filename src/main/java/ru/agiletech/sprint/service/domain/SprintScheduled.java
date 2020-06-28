package ru.agiletech.sprint.service.domain;

import lombok.Getter;
import ru.agiletech.sprint.service.domain.supertype.DomainEvent;
import ru.agiletech.sprint.service.domain.task.TaskId;

import java.util.Date;

@Getter
public class SprintScheduled extends DomainEvent {

    private SprintId sprintId;
    private TaskId taskId;

    public SprintScheduled(Date occurredOn,
                           String name,
                           SprintId sprintId,
                           TaskId taskId) {
        super(occurredOn, name);

        this.sprintId = sprintId;
        this.taskId = taskId;
    }

}
