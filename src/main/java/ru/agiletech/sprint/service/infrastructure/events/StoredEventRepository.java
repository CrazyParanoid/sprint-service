package ru.agiletech.sprint.service.infrastructure.events;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StoredEventRepository extends MongoRepository<StoredEvent, String> {

    Optional<StoredEvent> findFirstByStatus(StoredEvent.Status status);

}
