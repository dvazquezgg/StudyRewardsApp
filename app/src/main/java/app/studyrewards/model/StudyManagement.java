package app.studyrewards.model;

import android.app.Application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudyManagement extends Application implements Serializable {

    private List<Reward> rewards;
    private List<Task> tasks;
    private List<TaskLog> taskLogs;

    private int progress;
    private long timer;
    private int currentReward;

    public StudyManagement(){
        this.rewards = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.progress = 0;
        this.timer = 0;
        this.currentReward = 0;
        this.taskLogs = new ArrayList<>();
    }

    public StudyManagement(List<Reward> rewards, List<Task> tasks, int progress, long timer) {
        this.rewards = rewards;
        this.tasks = tasks;
        this.progress = progress;
        this.timer = timer;
        this.taskLogs = new ArrayList<>();
    }

    public static StudyManagement studyManagementFactory(){

        List<Reward> rewards = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        int progress = 0;
        long timer = 0 ;
        StudyManagement studyManagement = new StudyManagement(rewards, tasks, progress, 0);

        return studyManagement;
    }

    // Reward Methods
    public List<Reward> getRewards() {
        return rewards;
    }

    public Reward getCurrentReward(){
        return this.rewards.get(currentReward);
    }

    public void insertReward(Reward reward){
        if(this.rewards.contains(reward) == false){
            this.rewards.add(reward); // Only add if not there yet
        }
        currentReward = rewards.size()-1;
    }

    public void insertReward(String reward){
        Reward insertReward = new Reward(reward); // create reward with the String
        if(this.rewards.contains(insertReward) == false){
            this.rewards.add(insertReward); // Only add if not there yet
        }
        currentReward = rewards.size()-1;
    }

    // Task Methods
    public List<Task> getTasks() {
        return tasks;
    }
    public void setTask(Task task){this.tasks.add(task);}


    public int getProgress() {
        return progress;
    }

    public long getTimer() {
        return timer;
    }


    public List<TaskLog> getTaskLogs() {
        return taskLogs;
    }

    public TaskLog getTaskLog(int index) {
        return taskLogs.get(index);
    }

    public void setTaskLog(TaskLog taskLog){
        this.taskLogs.add(taskLog);
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setTaskLogs(List<TaskLog> taskLogs) {
        this.taskLogs = taskLogs;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public void setCurrentReward(int currentReward) {
        this.currentReward = currentReward;
    }

    public long updateProgress(){
        long totalTime = 0;
        for (Task task: tasks){
            long time = task.getTotalTime();
            totalTime = totalTime + time;
        }

        long studyTime = 0;
        for(TaskLog taskLog:taskLogs){
            long time = taskLog.getStopTime();
            studyTime = studyTime + time;
        }

        long progress = studyTime/totalTime;
        this.progress = (int) progress;
        return progress;
    }

}
