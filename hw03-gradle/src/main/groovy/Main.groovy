import io.micronaut.runtime.Micronaut
import service.ToDoListServiceImpl
import service.interfaces.ToDoListService

import java.time.LocalDate
import java.time.LocalDateTime

class Main {

    static void main(String[] args) {
        Micronaut.run(Main.class, args)
    }

//    private static ToDoListService service
//
//    static void main(String[] args) {
//        service = new ToDoListServiceImpl()
//        def input = System.console()
//        boolean stop = false
//
//        while (stop) {
//            println("App ToDo List")
//            println("Choose your action")
//            println("1 - add new task")
//            println("2 - add action in task")
//            println("3 - delete action from task")
//            println("4 - delete task")
//            println("5 - get tasks by day")
//            println("6 - get task by id")
//            println("7 - get actions by task")
//            println("8 - get count tasks by day")
//            println("9 - get busy time by day")
//
//            switch (input.readLine()) {
//                case 1:
//                    println("Input task name")
//                    String taskName = input.readLine()
//                    println("Input start time (yyyy-MM-ddThh:mm)")
//                    String timeStart = input.readLine()
//                    def start = LocalDateTime.parse(timeStart)
//                    println("Input end time (yyyy-MM-ddThh:mm)")
//                    String timeEnd = input.readLine()
//                    def end = LocalDateTime.parse(timeEnd)
//                    service.newTask(taskName, start, end)
//                    break
//                case 2:
//                    println("Input task number")
//                    int taskId = input.readLine() as int
//                    println("Input description action")
//                    String descAct = input.readLine()
//                    println("Input task time (HH mm)")
//                    String time = input.readLine()
//                    String[] timeArr = time.split(" ")
//                    service.addAction(taskId, descAct, timeArr[0] as int, timeArr[1] as int)
//                    break
//                case 3:
//                    println("Input task number")
//                    int taskId = input.readLine() as int
//                    println("Input action number")
//                    int actionId = input.readLine() as int
//                    service.deleteAction(taskId, actionId)
//                    break
//                case 4:
//                    println("Input task number")
//                    int taskId = input.readLine() as int
//                    service.deleteTask(taskId)
//                    break
//                case 5:
//                    println("Input day (yyyy MM dd)")
//                    String[] date = input.readLine().split(" ")
//                    LocalDate day = new LocalDate(date[0] as int, date[1] as int, date[2] as int)
//                    service.getListTasksByDay(day)
//                    break
//                case 6:
//                    println("Input task number")
//                    int taskId = input.readLine() as int
//                    service.getTaskById(taskId)
//                    break
//                case 7:
//                    println("Input task number")
//                    int taskId = input.readLine() as int
//                    service.getActionsByTask(taskId)
//                    break
//                case 8:
//                    println("Input day (yyyy MM dd)")
//                    String[] date = input.readLine().split(" ")
//                    LocalDate day = new LocalDate(date[0] as int, date[1] as int, date[2] as int)
//                    service.getCountTasksByDay(day)
//                    break
//                case 9:
//                    println("Input day (yyyy MM dd)")
//                    String[] date = input.readLine().split(" ")
//                    LocalDate day = new LocalDate(date[0] as int, date[1] as int, date[2] as int)
//                    service.getBusyTimeByDay(day)
//                    break
//            }
//        }
//    }
}
