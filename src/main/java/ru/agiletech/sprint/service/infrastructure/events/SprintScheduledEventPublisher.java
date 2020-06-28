package ru.agiletech.sprint.service.infrastructure.events;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.agiletech.sprint.service.domain.DomainEventPublisher;
import ru.agiletech.sprint.service.domain.SprintId;
import ru.agiletech.sprint.service.domain.SprintScheduled;
import ru.agiletech.sprint.service.domain.task.TaskId;
import ru.agiletech.sprint.service.infrastructure.persistence.RepositoryAccessException;

import java.util.*;

import static ru.agiletech.sprint.service.infrastructure.events.StoredEvent.Status.NEW;
import static ru.agiletech.sprint.service.infrastructure.events.StoredEvent.Status.PUBLISHED;

@Slf4j
@Service
@RequiredArgsConstructor
public class SprintScheduledEventPublisher implements DomainEventPublisher<SprintScheduled> {

    private static final String OCCURRED_ON = "occurredOn";
    private static final String NAME = "name";
    private static final String SPRINT_ID = "sprintId";
    private static final String TASK_ID = "taskId";

    private final StoredEventRepository storedEventRepository;
    private final Source source;

    @Override
    public void publish(List<SprintScheduled> domainEvents) {
        for(SprintScheduled event: domainEvents){
            String payload = new Gson().toJson(event);
            StoredEvent storedEvent = new StoredEvent(event.getName(),
                    new Date(),
                    payload,
                    NEW);

            try {
                storedEventRepository.save(storedEvent);
            } catch (MongoException ex){
                log.error(ex.getMessage());
                throw new RepositoryAccessException(ex.getMessage(), ex);
            }
        }
    }

    @Transactional
    @Scheduled(fixedDelayString = "${event.publisher.delay}")
    public void sendEventToBrokerFromDB(){
        try{
            Optional<StoredEvent> optionalStoredEvent = storedEventRepository.findFirstByStatus(NEW);

            if(optionalStoredEvent.isPresent()){
                StoredEvent storedEvent = optionalStoredEvent.get();
                SprintScheduled event = new Gson().fromJson(storedEvent.getPayload(), SprintScheduled.class);
                Map<String, Object> serializedEvent = serializeEvent(event);

                source.output().send(MessageBuilder
                        .withPayload(serializedEvent)
                        .build());
                storedEvent.setStatus(PUBLISHED);
                storedEventRepository.save(storedEvent);

                log.info("{} has been published", storedEvent.getEventType());
            }

        } catch (MessagingException ex){
            log.error(ex.getMessage());
            throw new MessagePublishingException(ex.getMessage(), ex);
        } catch (MongoException ex){
            log.error(ex.getMessage());
            throw new RepositoryAccessException(ex.getMessage(), ex);
        }
    }

    private Map<String, Object> serializeEvent(SprintScheduled event){
        Map<String, Object> serializedEvent = new HashMap<>();
        SprintId sprintId = event.getSprintId();
        TaskId taskId = event.getTaskId();

        serializedEvent.put(OCCURRED_ON, event.getOccurredOn());
        serializedEvent.put(NAME, event.getName());
        serializedEvent.put(SPRINT_ID, sprintId.getId());
        serializedEvent.put(TASK_ID, taskId.getId());

        return serializedEvent;
    }

}
