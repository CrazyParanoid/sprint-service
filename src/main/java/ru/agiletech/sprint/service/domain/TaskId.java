package ru.agiletech.sprint.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.agiletech.sprint.service.domain.supertype.ValueObject;

import java.util.Objects;

@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskId implements ValueObject {

    private String id;

    public static TaskId identifyTaskFrom(String id){
        return new TaskId(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        TaskId taskId = (TaskId) object;

        return Objects.equals(id,
                taskId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
