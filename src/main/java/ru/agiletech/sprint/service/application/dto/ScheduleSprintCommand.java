package ru.agiletech.sprint.service.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Команда на планирование спринта")
public class ScheduleSprintCommand {

    @NotEmpty(message = "Отсутствует идентификатор задачи")
    @ApiModelProperty(required = true, value = "Идентификатор задачи")
    private String taskId;

}
