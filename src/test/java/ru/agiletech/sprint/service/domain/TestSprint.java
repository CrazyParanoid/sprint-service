package ru.agiletech.sprint.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.agiletech.sprint.service.Application;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class TestSprint {

     private Sprint sprint;

     @Test
     public void testCreateSprint(){
         this.sprint = createSprint();

         assertNotNull(sprint.sprintId());
         assertNotNull(sprint.status());
     }

    @Test
    public void testScheduleSprint(){
        this.sprint = createSprint();

        String rawTaskId = UUID.randomUUID().toString();

        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        sprint.schedule(taskId);

        assertNotNull(sprint.tasks());
    }

    @Test
    public void testStartSprint(){
        this.sprint = createSprint();

        String rawTaskId = UUID.randomUUID().toString();

        TaskId taskId = TaskId.identifyTaskFrom(rawTaskId);
        sprint.schedule(taskId);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusWeeks(2);

        sprint.start(startDate, endDate);

        assertNotNull(sprint.status());
    }

    private Sprint createSprint(){
        return Sprint.create("AgileTech 0 Sprint",
                "The goal of sprint is to develop mvp sprint service",
                "TST");
    }

}
