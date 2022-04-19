package app.studyrewards.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.studyrewards.persistance.FileStorageAndroid;

/**
 * StudyManagement is the main class to store all information pertaining the app
 * implements Serializable to allow storing information in files
 */
public class StudyManagement extends Application implements Serializable {

    private List<Reward> rewards;
    private List<Task> tasks;
    private List<TaskLog> taskLogs;

    private int progress;
    private long timer;
    private int currentRewardIndex;

    private static final String TAG = "StudyManagement";

    public StudyManagement(){
        this.rewards = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.progress = 0;
        this.timer = 0;
        this.currentRewardIndex = -1; // no reward selected yet
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
    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public Reward getCurrentReward(){
        return this.rewards.get(currentRewardIndex);
    }
    public void setCurrentReward(int currentReward) {
        this.currentRewardIndex = currentReward;
    }
    public int getCurrentRewardIndex(){
        return this.currentRewardIndex;
    }

    public void insertReward(Reward reward){
        if(this.rewards.contains(reward) == false){
            this.rewards.add(reward); // Only add if not there yet
        }
        currentRewardIndex = rewards.size()-1;
    }

    public void insertReward(String reward){
        Reward insertReward = new Reward(reward); // create reward with the String
        if(this.rewards.contains(insertReward) == false){
            this.rewards.add(insertReward); // Only add if not there yet
        }
        currentRewardIndex = rewards.size()-1;
    }



    // Task Methods
    public List<Task> getTasks() {
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    public void setTask(Task task){this.tasks.add(task);}
    public Task getTask(int id){
        return this.tasks.get(id);
    }

    // TaskLog Methods
    public List<TaskLog> getTaskLogs() {
        return taskLogs;
    }
    public void setTaskLogs(List<TaskLog> taskLogs) {
        this.taskLogs = taskLogs;
    }
    public TaskLog getTaskLog(int index) {
        return taskLogs.get(index);
    }
    public void setTaskLog(TaskLog taskLog){
        this.taskLogs.add(taskLog);
    }

    public int getProgress() {
        updateProgress(); // check all task for time spent and time remaining
        return progress; // return the updated value
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getTimer() {
        return timer;
    }
    public void setTimer(long timer) {
        this.timer = timer;
    }

    public long getTotalTime(){
        long totalTime = 0;
        for (Task task: tasks){
            long time = task.getTotalTime();
            totalTime = totalTime + time;
        }
        return totalTime;
    }

    public long getStudyTime(){
        long studyTime = 0;
        for(TaskLog taskLog:taskLogs){
            long time = taskLog.getStopTime();
            studyTime = studyTime + time;
        }
        return studyTime;
    }

    public long updateProgress(){
        long progress = 0;
        if(getTotalTime() > 0) {
            progress = getStudyTime() / getTotalTime();
        }
        this.progress = (int) progress;
        return progress;
    }

    /**
     * Takes the content of the current object and saves it to the file system
     */
    public void saveData(){
        try {
            FileStorageAndroid.updateManagementData(getApplicationContext(), this);
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Reads a file form the system and attempts to load previous saved data
     * @param context
     * @param studyManagement
     */
    public static void loadFromDisk(Context context, StudyManagement studyManagement){

        // Attempt to read saved data
        try{
            StudyManagement management = FileStorageAndroid.getManagementData(context);
            studyManagement.setRewards(management.getRewards());
            studyManagement.setTasks(management.getTasks());
            studyManagement.setTaskLogs(management.getTaskLogs());
            studyManagement.setProgress(management.getProgress());
            studyManagement.setTimer(management.getTimer());
            studyManagement.setCurrentReward(management.getCurrentRewardIndex());
        } catch (IOException ioe){
            // NO StudyManagement in files
            Log.w(TAG, "getStudyManagement: No Data saved found.",ioe);
        }
    }
}
