package ru.agiletech.sprint.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import ru.agiletech.sprint.service.domain.supertype.ValueObject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintPeriod implements ValueObject {

    @Transient
    static long ZERO_DAYS = 0;
    @Transient
    static LocalDate EMPTY_DATE = null;

    private LocalDate startDate;
    private LocalDate endDate;

    static SprintPeriod between(LocalDate startDate,
                                LocalDate endDate){
        return new SprintPeriod(startDate,
                endDate);
    }

    public long calculateDays(){
        Period period = Period.between(this.startDate, this.endDate);

        return period.getDays();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null
                || getClass() != object.getClass())
            return false;

        SprintPeriod sprintPeriod = (SprintPeriod) object;

        return Objects.equals(startDate, sprintPeriod.startDate) &&
                Objects.equals(endDate, sprintPeriod.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

}
