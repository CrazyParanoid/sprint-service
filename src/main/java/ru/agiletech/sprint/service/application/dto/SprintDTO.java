package ru.agiletech.sprint.service.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Представление модели спринта")
public class SprintDTO extends RepresentationModel<SprintDTO> {

    @NotEmpty(message = "Отсутствует наименование спринта")
    @ApiModelProperty(required = true, value = "Наименование")
    private String name;

    @NotEmpty(message = "Отсутствует цель спринта")
    @ApiModelProperty(position = 1, required = true, value = "Цель")
    private String goal;

    @NotEmpty(message = "Отсутствует ключ проекта")
    @ApiModelProperty(position = 2, required = true, value = "Ключ проекта")
    private String projectKey;

    @ApiModelProperty(position = 3, value = "Идентификатор", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String id;

    @ApiModelProperty(position = 4, value = "Статус", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String status;

    @ApiModelProperty(position = 5, value = "Список задач в спринте", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Set<String> tasks;

    @ApiModelProperty(position = 6, value = "Количество дней в спринте", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private long sprintDays;

    @ApiModelProperty(position = 7, value = "Дата начала спринта", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private LocalDate startDate;

    @ApiModelProperty(position = 8, value = "Дата окончания спринта", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private LocalDate endDate;

}
