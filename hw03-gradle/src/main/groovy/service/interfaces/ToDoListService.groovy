package service.interfaces

import controller.dto.TaskDto

import java.time.LocalDate
import java.time.LocalDateTime

interface ToDoListService {

    newTask(String taskName, LocalDateTime taskStart, LocalDateTime endTask)

    getTaskById(int id)

    addAction(int taskId, String action, int h, int m)

    getActionsByTask(int taskId)

    deleteTask(int taskId)

    deleteAction(int taskId, int actionId)

    getListTasksByDay(LocalDate day)

    getCountTasksByDay(LocalDate day)

    getBusyTimeByDay(LocalDate day)
}