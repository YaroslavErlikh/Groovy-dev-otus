package service

import com.sun.tools.javac.util.Pair
import model.Action
import model.Task
import repo.Repo
import service.interfaces.ToDoListService

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ToDoListServiceImpl implements ToDoListService {

    private Repo repo

    ToDoListServiceImpl() {
        repo = Repo.instance
    }

    @Override
    def newTask(String newTaskName, LocalDateTime newStartTask, LocalDateTime newEndTask) {
        def newTask = new Task()
        newTask.with {
            taskName = newTaskName
            startTask = newStartTask
            endTask = newEndTask
        }
        if (checkTaskTime(newTask)) {
            repo.addTask(newTask)
            return true
        } else {
            return false
        }
    }

    private def checkTaskTime(Task task) {
        def tasks = repo.getListTasks()
        def map = tasks.groupBy {
            new Pair(it.startTask, it.endTask)
        }
        def find = map.find {
            if ((it.key.fst as LocalDateTime) == task.startTask ||
                    ((it.key.fst as LocalDateTime).isAfter(task.startTask) &&
                            (it.key.snd as LocalDateTime).isBefore(task.startTask))) {
                return true
            }
            if (((it.key.snd as LocalDateTime) == task.endTask) ||
                    (it.key.snd as LocalDateTime).isBefore(task.endTask) &&
                    (it.key.fst as LocalDateTime).isAfter(task.endTask)) {
                return true
            }
            return false
        }.collect(it -> it.key)

        if (find.isEmpty()) {
            return true
        } else {
            println("Task not added, time crossing")
            return false
        }
    }

    @Override
    def getTaskById(int id) {
        def task = repo.getTask(id)

        if (!task) {
            println("Task with id=${id} not found")
            return null
        }

        return task
    }

    @Override
    def addAction(int taskId, String newActionName, int h, int m) {
        def task = getTaskById(taskId)
        def newActionTime = LocalTime.of(h, m)
        def newAction = new Action(newActionName, newActionTime)

        def computeTime = task.startTask.plus(newActionTime.toSecondOfDay())
        if (computeTime.isAfter(task.endTask)) {
            return "Time exceeds task completion time by ${computeTime.minus(task.endTask.second)} minutes, no action added"
        }

        def map = task.actionList.groupBy { it.actionId }
        newAction.with {
            actionId = map.keySet().max() + 1
        }

        task.actionList << newAction

        repo.modifyTask(task)
    }

    @Override
    def getActionsByTask(int taskId) {
        def task = getTaskById(taskId)

        task.actionList
    }

    @Override
    def deleteTask(int taskId) {
        def task = getTaskById(taskId)

        repo.deleteTask(task)
    }

    @Override
    def deleteAction(int taskId, int delActionId) {
        def task = getTaskById(taskId)

        def actionList = task.actionList
        def find = actionList.find { it.actionId == delActionId }

        if (!find) {
            println("Action with id=${delActionId} not found in Task ${task.taskName}")
            return null
        }

        actionList.remove(find)
        task.actionList = actionList
    }

    @Override
    def getListTasksByDay(LocalDate day) {
        def tasks = repo.listTasks
        def ldtStart = day.atStartOfDay()
        def ldtEnd = (day + 1).atStartOfDay()

        def result = tasks.findAll {
            it.startTask.isAfter(ldtStart) && it.endTask.isAfter(ldtEnd)
        }
        return result
    }

    @Override
    def getCountTasksByDay(LocalDate day) {
        return getListTasksByDay(day).size()
    }

    @Override
    def getBusyTimeByDay(LocalDate day) {
        def listTasks = getListTasksByDay(day)

        def groupByTime = listTasks.groupBy {
            new Pair(it.startTask, it.endTask)
        }

        return groupByTime
    }

}
