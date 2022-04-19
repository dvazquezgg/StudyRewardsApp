package app.studyrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;

import app.studyrewards.model.StudyManagement;
import app.studyrewards.model.Task;
import app.studyrewards.persistance.FileStorageAndroid;

/**
 * Initial Activity
 * 1) Activity reads configuration file
 * 2) If configuration exists progress is displayed
 * 3) Otherwise user is offered to setup a rewards an start some tasks
 *
 * extends AppCompatActivity as a default activity in Android
 * implements View.OnClickListener to respond to buttons
 *
 */
public class ReviewTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progress;
    private TextView tvSelectedReward;
    private RadioGroup rgTasks;
    private Button btnAddTask;
    private Button btnStudyTime;

    String TAG = "ReviewTaskActivity";
    StudyManagement management; // General StudyManagement object to store app information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_task);

        management = (StudyManagement) getApplicationContext();
        StudyManagement.loadFromDisk(getApplicationContext(),management);

        if(management.getCurrentRewardIndex() == -1) { // No reward selected in the system yet
            Intent myIntent = new Intent(ReviewTaskActivity.this, SetupActivity.class);
            ReviewTaskActivity.this.startActivity(myIntent);
        }

        // Generation radio buttons from each task in registered in the app
        rgTasks = findViewById(R.id.rgTasks);
        rgTasks.removeAllViews();
        // Looping all tasks
        for(Task task:management.getTasks()){
            RadioButton radio = new RadioButton(this);
            if (task.getViewId() == -1) {
                radio.setId(View.generateViewId()); // create a resource id from the app
                task.setViewId(radio.getId());
            } else{
                radio.setId(task.getViewId());
            }
            radio.setText(task.getTitle());
            radio.setOnClickListener(this);
            rgTasks.addView(radio);  // adding the generated radio button to the group
        }

        progress = findViewById(R.id.progressBar);
        int maxValue=progress.getMax();
        int progressValue=progress.getProgress();
        progress.setProgress(management.getProgress()); // 50 default progress value for the progress bar

        tvSelectedReward = findViewById(R.id.tvSelectedReward);
        String rewardTitle = management.getCurrentRewardIndex() == -1 ? "No reward selected": management.getCurrentReward().getDescription();
        tvSelectedReward.setText(rewardTitle);

        btnAddTask = findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(this);

        btnStudyTime = findViewById(R.id.btnStudyTime);
        btnStudyTime.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if (v == btnStudyTime) {
            int selectedTask = rgTasks.getCheckedRadioButtonId();
            Log.d("ReviewTask", String.valueOf(selectedTask));
            int taskIndex = selectedTask - 1; // the task from the radio button - 1 to get index
            Task task = management.getTask(taskIndex);
            Log.d("ReviewTask", task.toString());

            Intent myIntent = new Intent(ReviewTaskActivity.this, StudyTimeActivity.class);
            myIntent.putExtra("task", task.getTitle());
            myIntent.putExtra("taskIndex", taskIndex);
            ReviewTaskActivity.this.startActivity(myIntent);
        }
        else if (v == btnAddTask){
            Intent myIntent = new Intent(ReviewTaskActivity.this, AddTaskActivity.class);
            ReviewTaskActivity.this.startActivity(myIntent);
        }
    }
}