package ru.agiletech.sprint.service.domain.supertype;

import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.annotation.Transient;
import ru.agiletech.sprint.service.domain.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot extends IdentifiedDomainObject
        implements Entity{

    @Getter
    @Transient
    private List<DomainEvent> domainEvents;

    protected void registerDomainEvent(DomainEvent domainEvent){
        if(CollectionUtils.isEmpty(this.domainEvents))
            this.domainEvents = new ArrayList<>();

        this.domainEvents.add(domainEvent);
    }

}
