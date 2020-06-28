package ru.agiletech.sprint.service.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Команда для старта спринта")
public class StartSprintCommand {

    @NotNull(message = "Отсутствует дата начала спринта")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(required = true, value = "Дата начала спринта")
    private LocalDate startDate;

    @Future(message = "Некорректное значение даты окончания спринта")
    @NotNull(message = "Отсутствует дата начала спринта")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @ApiModelProperty(position = 1, required = true, value = "Дата конца спринта")
    private LocalDate endDate;

}
