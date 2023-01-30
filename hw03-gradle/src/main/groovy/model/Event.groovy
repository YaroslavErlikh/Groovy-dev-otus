package model

import java.time.LocalDateTime

class Event {

    String taskName
    LocalDateTime taskStartTime

    Event(String taskName, LocalDateTime taskStartTime) {
        this.taskName = taskName
        this.taskStartTime = taskStartTime
    }
}
