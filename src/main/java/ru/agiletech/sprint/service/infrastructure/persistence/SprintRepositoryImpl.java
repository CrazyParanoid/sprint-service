package ru.agiletech.sprint.service.infrastructure.persistence;

import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.agiletech.sprint.service.domain.Sprint;
import ru.agiletech.sprint.service.domain.SprintId;
import ru.agiletech.sprint.service.domain.SprintRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class SprintRepositoryImpl implements SprintRepository {

    private final SprintMongoDbRepository sprintMongoDbRepository;

    @Override
    public void save(Sprint sprint) {
        try {
            sprintMongoDbRepository.save(sprint);
        } catch (MongoException ex){
            log.error(ex.getMessage());
            throw new RepositoryAccessException(ex.getMessage(), ex);
        }
    }

    @Override
    public Sprint sprintOfId(SprintId sprintId) {
        try {
            return sprintMongoDbRepository.findBySprintId(sprintId)
                    .orElseThrow(() -> new SprintNotFoundException("Sprint is not found"));
        } catch (MongoException ex){
            log.error(ex.getMessage());
            throw new RepositoryAccessException(ex.getMessage(), ex);
        }
    }

    @Override
    public Set<Sprint> allSprints() {
        try {
            return new HashSet<>(sprintMongoDbRepository.findAll());
        } catch (MongoException ex){
            log.error(ex.getMessage());
            throw new RepositoryAccessException(ex.getMessage(), ex);
        }
    }

}
