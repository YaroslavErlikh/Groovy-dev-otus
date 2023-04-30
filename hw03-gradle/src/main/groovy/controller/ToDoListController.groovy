package controller

import com.github.javaparser.utils.Pair
import controller.dto.ActionDto
import controller.dto.TaskDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject
import model.Action
import model.Task
import service.interfaces.ToDoListService

import java.security.cert.LDAPCertStoreParameters
import java.time.LocalDate

@Controller("/toDoList")
class ToDoListController {

    @Inject
    private ToDoListService service

    @Post(uri = "/newTask", produces = MediaType.APPLICATION_JSON)
    HttpResponse<Boolean> newTask(TaskDto taskDto) {
        Boolean result = service.newTask(taskDto.taskName, taskDto.taskStart, taskDto.taskEnd)
        return HttpResponse.ok(result)
    }

    @Get(uri = "/taskById", produces = MediaType.APPLICATION_JSON)
    HttpResponse<TaskDto> getTaskById(TaskDto taskDto) {
        def task = service.getTaskById(taskDto.taskId)
        TaskDto response = new TaskDto()
        response.setTaskId(task.taskId)
        response.setTaskName(task.taskName)
        response.setTaskStart(task.startTask)
        response.setTaskEnd(task.endTask)

        return HttpResponse.ok(response)
    }

    @Post(uri = "/addAction", produces = MediaType.APPLICATION_JSON)
    HttpResponse<Object> addACtion(ActionDto actionDto) {
        def result = service.addAction(actionDto.taskId,actionDto.actionName, actionDto.hour, actionDto.minutes)
        return HttpResponse.ok(result)
    }

    @Get(uri = "/getActionsByTask", produces = MediaType.APPLICATION_JSON)
    HttpResponse<List<ActionDto>> getActionsByTask(TaskDto taskDto) {
        List<Action> taskActions = service.getActionsByTask(taskDto.taskId)
        List<ActionDto> actionDtoList = []
        taskActions.each {
            def action = new ActionDto()
            action.setTaskId(taskDto.taskId)
            action.setActionId(it.actionId)
            action.setActionName(it.action)
            action.setDuration(it.duration)

            actionDtoList << action
        }

        return HttpResponse.ok(actionDtoList)
    }

    @Delete(uri = "/deleteTask", produces = MediaType.APPLICATION_JSON)
    HttpResponse<Boolean> deleteTask(TaskDto taskDto) {
        Boolean result = service.deleteTask(taskDto.taskId)
        return HttpResponse.ok(result)
    }

    @Delete(uri = "/deleteAction", produces = MediaType.APPLICATION_JSON)
    HttpResponse<Boolean> deleteAction(ActionDto actionDto) {
        Boolean result = service.deleteAction(actionDto.taskId, actionDto.actionId)
        return HttpResponse.ok(result)
    }

    @Get(uri = "/tsksByDay", produces = MediaType.APPLICATION_JSON)
    HttpResponse<List<TaskDto>> tasksByDay(LocalDate date) {
        List<Task> tasks = service.getListTasksByDay(date)
        List<TaskDto> taskDtoList = []
        tasks.each {
            def taskDto = new TaskDto()
            taskDto.setTaskId(it.taskId)
            taskDto.setTaskName(it.taskName)
            taskDto.setActionDtoList(it.actionList)
            taskDto.setTaskStart(it.startTask)
            taskDto.setTaskEnd(it.endTask)

            taskDtoList << taskDto
        }

        return HttpResponse.ok(taskDtoList)
    }

    @Get(uri = "/countTasksByDay", produces = MediaType.APPLICATION_JSON)
    HttpResponse<Integer> countTasksByDay(LocalDate date) {
        Integer result = service.getCountTasksByDay(date)
        return HttpResponse.ok(result)
    }

    @Get(uri = "/busyTimeByDay", produces = MediaType.APPLICATION_JSON)
    HttpResponse<Map<Pair, List<TaskDto>>> busyTimeByDay(LocalDate date) {
        Map<Pair, List<Task>> map = service.getBusyTimeByDay(date)
        Map<Pair, List<TaskDto>> result = [:]
        map.entrySet().each {
            it.getValue().each {v ->
                def taskDto = new TaskDto()
                taskDto.setTaskId(v.taskId)
                taskDto.setTaskName(v.taskName)

                List<ActionDto> actionDtoList = []
                v.actionList.each { action ->
                    ActionDto actionDto = new ActionDto()
                    actionDto.setTaskId(v.taskId)
                    actionDto.setActionId(action.actionId)
                    actionDto.setActionName(action.action)
                    actionDto.setDuration(action.duration)

                    actionDtoList << actionDto
                }
                taskDto.setActionDtoList(actionDtoList)
                taskDto.setTaskStart(v.startTask)
                taskDto.setTaskEnd(v.endTask)

                result << [it.key : taskDto]
            }
        }

        return HttpResponse.ok(result)
    }
}
