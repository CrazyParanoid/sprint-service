package ru.agiletech.sprint.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import ru.agiletech.sprint.service.domain.task.TaskId;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static ru.agiletech.sprint.service.domain.Sprint.*;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SprintSnapshot {

    private final SprintPeriod  period;
    private final Status        status;
    private final Set<TaskId>   tasks;

    public String getStatus(){
        return this.status.getValue();
    }

    public long daysOfSprint(){
        if(status == Status.ACTIVE)
            return this.period.calculateDays();

        return SprintPeriod.ZERO_DAYS;
    }

    public Set<String> getTasks(){
        if(CollectionUtils.isNotEmpty(this.tasks)){
            Set<String> tasks = new HashSet<>();
            this.tasks.forEach(task -> tasks.add(task.getId()));

            return tasks;
        }

        return Collections.emptySet();
    }

    public LocalDate getStartDateOfSprint(){
        if(status == Status.ACTIVE)
            return this.period.getStartDate();

        return SprintPeriod.EMPTY_DATE;
    }

    public LocalDate getEndDateOfSprint(){
        if(status == Status.ACTIVE)
            return this.period.getEndDate();

        return SprintPeriod.EMPTY_DATE;
    }

}
