package ru.agiletech.sprint.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Project {

    private String key;

    static Project createFrom(String projectKey){
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
