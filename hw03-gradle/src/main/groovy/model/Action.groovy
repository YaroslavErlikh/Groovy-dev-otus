package model


import java.time.LocalTime

class Action {

    int actionId
    String action
    LocalTime duration

    Action(int actionId, String action, LocalTime duration) {
        this.actionId = actionId
        this.action = action
        this.duration = duration
    }

    Action(String action, LocalTime duration) {
        this.action = action
        this.duration = duration
    }
}
