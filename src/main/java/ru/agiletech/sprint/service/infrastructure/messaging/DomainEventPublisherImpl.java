package ru.agiletech.sprint.service.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.agiletech.sprint.service.domain.DomainEvent;
import ru.agiletech.sprint.service.domain.DomainEventPublisher;

import java.util.List;

@Slf4j
@Service
public class DomainEventPublisherImpl implements DomainEventPublisher {

    @Override
    public void publish(List<DomainEvent> domainEvents) {

    }

}
