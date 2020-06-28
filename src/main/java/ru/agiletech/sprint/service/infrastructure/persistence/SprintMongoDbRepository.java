package ru.agiletech.sprint.service.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.agiletech.sprint.service.domain.Sprint;
import ru.agiletech.sprint.service.domain.SprintId;

import java.util.Optional;

public interface SprintMongoDbRepository extends MongoRepository<Sprint, String>{

    Optional<Sprint> findBySprintId(SprintId id);

}
