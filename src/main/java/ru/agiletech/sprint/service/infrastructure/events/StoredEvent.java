package ru.agiletech.sprint.service.infrastructure.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stored_events")
public class StoredEvent {

    @Id
    private String id;
    private String eventType;
    private Date storeDate;
    private String payload;
    private Status status;


    public StoredEvent(String eventType, Date storeDate,
                       String payload, Status status) {
        this.eventType = eventType;
        this.storeDate = storeDate;
        this.payload = payload;
        this.status = status;
    }

    public enum Status{
        NEW,
        PUBLISHED
    }

}
