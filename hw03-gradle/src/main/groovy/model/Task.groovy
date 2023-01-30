package model

import java.time.LocalDateTime

class Task {

    int taskId
    String taskName
    List<Action> actionList
    LocalDateTime startTask
    LocalDateTime endTask

    Task(int taskId, String taskName, List<Action> actionList, LocalDateTime startTask, LocalDateTime endTask) {
        this.taskId = taskId
        this.taskName = taskName
        this.actionList = actionList
        this.startTask = startTask
        this.endTask = endTask
    }

    Task(String taskName, List<Action> actionList, LocalDateTime startTask, LocalDateTime endTask) {
        this.taskName = taskName
        this.actionList = actionList
        this.startTask = startTask
        this.endTask = endTask
    }

    Task() {
    }
}
