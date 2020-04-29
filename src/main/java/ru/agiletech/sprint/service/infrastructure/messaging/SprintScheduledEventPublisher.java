package ru.agiletech.sprint.service.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.agiletech.sprint.service.domain.DomainEventPublisher;
import ru.agiletech.sprint.service.domain.SprintId;
import ru.agiletech.sprint.service.domain.SprintScheduled;
import ru.agiletech.sprint.service.domain.TaskId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SprintScheduledEventPublisher
        implements DomainEventPublisher<SprintScheduled> {

    private static final String OCCURRED_ON     = "occurredOn";
    private static final String NAME            = "name";
    private static final String SPRINT_ID       = "sprintId";
    private static final String TASK_ID         = "taskId";

    private final Source source;

    @Override
    public void publish(List<SprintScheduled> domainEvents) {
        try{
            for(SprintScheduled event: domainEvents){
                Map<String, Object> serializedEvent = new HashMap<>();

                SprintId sprintId = event.getSprintId();
                TaskId taskId = event.getTaskId();

                serializedEvent.put(OCCURRED_ON, event.getOccurredOn());
                serializedEvent.put(NAME, event.getName());
                serializedEvent.put(SPRINT_ID, sprintId.getId());
                serializedEvent.put(TASK_ID, taskId.getId());

                source.output()
                        .send(MessageBuilder
                                .withPayload(serializedEvent)
                                .build());
            }
        } catch (MessagingException ex){
            log.error(ex.getMessage());

            throw new MessagePublishingException(ex.getMessage(), ex);
        }
    }

}
