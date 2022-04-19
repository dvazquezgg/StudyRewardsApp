package app.studyrewards.model;
import java.io.Serializable;
import java.util.Date;

/**
 * Task class to represent available task to complete for the reward
 * implements Serializable to allow storing information in files
 */
public class Task implements Serializable {

    private String title;
    private Date deadline;
    private long totalTime;
    private long spentTime;
    private boolean completed;
    private int viewId;

    public Task(String title, Date deadline, long timeInMinutes) {
        this.title = title;
        this.deadline = deadline;
        long timeInSeconds = timeInMinutes * 60;
        this.totalTime = timeInSeconds;
        this.spentTime = 0;
        this.completed = false;
        this.viewId = -1;
    }

    public boolean isCompleted() {
        if(this.spentTime >= this.totalTime){
            this.completed = true;
        }
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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

    public void setSpentTime(long spentTimeInSconds) {
        this.spentTime = this.spentTime + spentTimeInSconds;
    }

    // variable to hold the view ID generated by the system
    // to display radio buttons
    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", deadline=" + deadline +
                ", totalTime=" + totalTime +
                ", spentTime=" + spentTime +
                ", completed=" + completed +
                '}';
    }
}
