package ru.agiletech.sprint.service.domain.project;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Project {

    private String key;

    public static Project createFrom(String projectKey){
        return new Project(projectKey);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        Project project = (Project) object;

        return Objects.equals(key,
                project.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

}
