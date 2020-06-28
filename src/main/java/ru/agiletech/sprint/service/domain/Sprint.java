package ru.agiletech.sprint.service.domain;

import lombok.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.agiletech.sprint.service.domain.project.Project;
import ru.agiletech.sprint.service.domain.supertype.AggregateRoot;
import ru.agiletech.sprint.service.domain.task.TaskId;

import java.time.LocalDate;;
import java.util.*;

@Document(collection = "sprints")
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sprint extends AggregateRoot {

    private SprintId sprintId;
    private SprintPeriod period;
    private Project project;
    private String name;
    private String goal;
    private Status status;
    private Set<TaskId> tasks;

    private Sprint(SprintId sprintId,
                   Project project,
                   String goal,
                   Set<TaskId> tasks,
                   String name,
                   Status status) {
        this.sprintId = sprintId;
        this.project = project;
        this.goal = goal;
        this.tasks = tasks;
        this.status = status;
        this.name = name;
    }

    public void start(LocalDate startDate, LocalDate endDate){
        checkReadyForStart();
        this.period = SprintPeriod.between(startDate, endDate);
        this.status = Status.ACTIVE;
    }

    private void checkReadyForStart(){
        if(this.status != Status.INACTIVE)
            throw new UnsupportedOperationException("Невозможно начать спринт. " +
                    "Возможно спринт уже был взят в работу");
        if(CollectionUtils.isEmpty(this.tasks))
            throw new UnsupportedOperationException("Невозможно начать спринт. " +
                    "Отсутствуют запланированные задачи");
    }

    public SprintScheduled schedule(String rawTaskId){
        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        if(this.status == Status.COMPLETE)
            throw new UnsupportedOperationException("Невозможно спланировать завершенный спринт.");
        this.tasks.add(taskId);

        String eventName = SprintScheduled.class.getName();
        return new SprintScheduled(new Date(),
                eventName,
                this.sprintId,
                taskId);
    }

    public void complete(){
        if(status != Status.ACTIVE)
            throw new UnsupportedOperationException("Невозможно завершить спринт.");
        this.status = Status.COMPLETE;
    }

    public String sprintId(){
        return this.sprintId.getId();
    }

    public SprintSnapshot makeSnapshot(){
        return new SprintSnapshot(this.period,
                this.status,
                this.tasks);
    }

    public static Sprint create(String name, String goal, String projectKey){
        SprintId sprintId = SprintId.identifySprint();
        Project project = Project.createFrom(projectKey);

        return new Sprint(sprintId,
                project,
                goal,
                new HashSet<>(),
                name,
                Status.INACTIVE);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Sprint sprint = (Sprint) object;

        return Objects.equals(sprintId, sprint.sprintId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sprintId);
    }

    @RequiredArgsConstructor
    public enum Status{
        INACTIVE("INACTIVE"),
        ACTIVE("ACTIVE"),
        COMPLETE("COMPLETE");

        @Getter
        private final String value;
    }

}
