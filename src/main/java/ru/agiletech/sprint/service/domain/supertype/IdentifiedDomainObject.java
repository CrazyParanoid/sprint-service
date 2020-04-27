package ru.agiletech.sprint.service.domain.supertype;

import org.springframework.data.annotation.Id;

public class IdentifiedDomainObject {

    @Id
    private String id;

}
