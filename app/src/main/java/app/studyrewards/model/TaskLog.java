package app.studyrewards.model;

import java.io.Serializable;

public class TaskLog implements Serializable {

    private long startTime;
    private long stopTime;

    private String message;
    private Task task;

    public TaskLog(long startTime, long stopTime, String message, Task task) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.message = message;
        this.task = task;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TaskLog{" +
                "startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", message='" + message + '\'' +
                ", task=" + task +
                '}';
    }
}
