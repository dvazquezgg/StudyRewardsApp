package app.studyrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.studyrewards.model.StudyManagement;
import app.studyrewards.model.Task;
import app.studyrewards.model.TaskLog;
import app.studyrewards.util.TimeConvert;

/**
 * AddTaskLogActivity activity to create a new log after a study session
 * extends AppCompatActivity as a default activity in Android
 * implements View.OnClickListener to respond to buttons
 */
public class AddTaskLogActivity extends AppCompatActivity implements View.OnClickListener {

    TextView recordedTimeTV;
    TextView lblTaskLogTitle;
    EditText editTextTextMultiLine;
    Button btnSave;

    private Task currentTask;
    private String taskTitle;
    private int taskIndex;
    private long sessionTime;
    private long startTime;

    String TAG = "AddTaskLogActivity";
    StudyManagement management;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_log);

        Intent intent = getIntent();
        taskTitle = intent.getStringExtra("task");
        taskIndex = intent.getIntExtra("taskIndex",0);
        sessionTime = intent.getLongExtra("sessionTime", 0);
        startTime = intent.getLongExtra("startTime", 0);

        management = (StudyManagement) getApplicationContext();

        Log.d(TAG,"storage :" + management.toString());
        currentTask = management.getTask(taskIndex);

        lblTaskLogTitle = findViewById(R.id.lblTaskLogTitle);
        String screenTitle = lblTaskLogTitle.getText().toString() + " " + taskTitle;
        lblTaskLogTitle.setText(screenTitle);

        recordedTimeTV= findViewById(R.id.recordedTime);
        recordedTimeTV.setText(TimeConvert.convertMillisToTime(sessionTime));

        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSave) {
            String message = editTextTextMultiLine.getText().toString();
            long time = sessionTime / 1000;  // session time is in milliseconds
            TaskLog taskLog = new TaskLog(startTime, time, message, currentTask);
            currentTask.setSpentTime(time);
            management.setTaskLog(taskLog);
            management.saveData();

            Intent myIntent = new Intent(AddTaskLogActivity.this, ReviewTaskActivity.class);
            AddTaskLogActivity.this.startActivity(myIntent);

        }
    }
}