package ru.agiletech.sprint.service.domain;

import ru.agiletech.sprint.service.domain.supertype.DomainEvent;

import java.util.List;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(List<T> domainEvents);

}
