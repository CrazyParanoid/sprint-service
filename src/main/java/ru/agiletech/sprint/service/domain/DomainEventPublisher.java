package ru.agiletech.sprint.service.domain;

import java.util.List;

public interface DomainEventPublisher {

    void publish(List<DomainEvent> domainEvents);

}
