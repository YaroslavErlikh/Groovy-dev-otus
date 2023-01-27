package repo

import model.Task

@Singleton
class Repo {

    List<Task> tasks = new ArrayList<>()

    def addTask(Task newTask) {
        def max = tasks.max {it.taskId}
        newTask.with {
            taskId = max.taskId + 1
        }
        tasks << newTask
    }

    def getTask(int id) {
        tasks.find {it.taskId == id}
    }

    def modifyTask(Task task) {
        def find = getTask(task.taskId)
        deleteTask(find)
        addTask(task)
    }

    def deleteTask(Task deleteTask) {
        tasks.remove(deleteTask)
    }

    def getCountTasks() {
        tasks.size()
    }

    getListTasks() {
        tasks
    }
}
