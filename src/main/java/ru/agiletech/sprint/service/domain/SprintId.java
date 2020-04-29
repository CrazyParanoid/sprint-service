package ru.agiletech.sprint.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.agiletech.sprint.service.domain.supertype.ValueObject;

import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintId implements ValueObject {

    private String id;

    static SprintId identifySprint(){
        String id = UUID.randomUUID().toString();

        return new SprintId(id);
    }

    public static SprintId identifySprint(String id){
        return new SprintId(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        SprintId sprintId = (SprintId) object;
        return Objects.equals(id,
                sprintId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
