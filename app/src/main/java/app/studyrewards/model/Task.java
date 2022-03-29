package app.studyrewards.model;
import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

    private String title;
    private Date deadline;
    private long totalTime;
    private long spentTime;

    public Task(String title, Date deadline, long totalTime) {
        this.title = title;
        this.deadline = deadline;
        this.totalTime = totalTime;
        this.spentTime = 0;
    }

    public String getTitle() {
        return title;
    }

    public Date getDeadline() {
        return deadline;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public long getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(long spentTime) {
        this.spentTime = spentTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", deadline=" + deadline +
                ", totalTime=" + totalTime +
                ", spentTime=" + spentTime +
                '}';
    }
}
