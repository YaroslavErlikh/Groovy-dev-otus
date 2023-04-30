package controller.dto;

import java.time.LocalDateTime;

public class TaskDto {

    Integer taskId
    String taskName
    List<ActionDto> actionDtoList
    LocalDateTime taskStart
    LocalDateTime taskEnd
}