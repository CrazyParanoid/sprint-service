package ru.agiletech.sprint.service.domain;

import lombok.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.agiletech.sprint.service.domain.supertype.AggregateRoot;

import java.time.LocalDate;;
import java.util.*;

@Document(collection = "sprints")
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sprint extends AggregateRoot {

    private SprintId        sprintId;
    private SprintPeriod    period;
    private String          name;
    private String          goal;
    private Status          status;
    private Set<TaskId>     tasks;

    private Sprint(SprintId     sprintId,
                   String       goal,
                   Set<TaskId>  tasks,
                   String       name,
                   Status       status) {
        this.sprintId   = sprintId;
        this.goal       = goal;
        this.tasks      = tasks;
        this.status     = status;
        this.name       = name;
    }

    public SprintStarted start(LocalDate startDate,
                               LocalDate endDate){
        if(this.status != Status.INACTIVE)
            throw new UnsupportedOperationException("Wrong operation for current state");

        if(Optional.ofNullable(startDate).isEmpty()
                || Optional.ofNullable(endDate).isEmpty())
            throw new IllegalArgumentException("Wrong period values");

        this.period = SprintPeriod.between(startDate, endDate);
        this.status = Status.ACTIVE;

        String eventName = SprintStarted.class.getName();

        return new SprintStarted(new Date(),
                eventName,
                this.sprintId);
    }

    public void schedule(TaskId taskId){
        if(this.status == Status.COMPLETE)
            throw new UnsupportedOperationException("Wrong operation for current state");

        this.tasks.add(taskId);
    }

    public long daysOfSprint(){
        if(status == Status.ACTIVE)
            return this.period.calculateDays();

        return SprintPeriod.ZERO_DAYS;
    }

    public LocalDate startDateOfSprint(){
        if(status == Status.ACTIVE)
            return this.period.getStartDate();

        return SprintPeriod.EMPTY_DATE;
    }

    public LocalDate endDateOfSprint(){
        if(status == Status.ACTIVE)
            return this.period.getEndDate();

        return SprintPeriod.EMPTY_DATE;
    }

    public void complete(){
        this.status = Status.COMPLETE;
    }

    public String status(){
        return this.status.getValue();
    }

    public Set<String> tasks(){
        if(CollectionUtils.isNotEmpty(this.tasks)){
            Set<String> tasks = new HashSet<>();
            this.tasks.forEach(task -> tasks.add(task.getId()));

            return tasks;
        }

        return Collections.emptySet();
    }

    public String sprintId(){
        return this.sprintId.getId();
    }

    public static Sprint create(String          name,
                                String          goal){
        SprintId sprintId = SprintId.identifySprint();

        return new Sprint(sprintId,
                goal,
                new HashSet<>(),
                name,
                Status.INACTIVE);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        Sprint sprint = (Sprint) object;

        return Objects.equals(sprintId,
                sprint.sprintId);
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

        @Getter(value = AccessLevel.PRIVATE)
        private final String value;
    }

}
