package app.studyrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import app.studyrewards.model.StudyManagement;
import app.studyrewards.model.Task;
import app.studyrewards.persistance.FileStorageAndroid;
import app.studyrewards.util.TimeConvert;

/**
 * StudyTimeActivity activity manage the Countdown and accumulate session time
 * extends AppCompatActivity as a default activity in Android
 * implements View.OnClickListener to respond to buttons
 */
public class StudyTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private Chronometer simpleChronometer;
    private CountDownTimer countDown;
    private TextView totalTimeTV;
    private TextView sessionTimeTV;
    private TextView lblTaskTitle;
    private TextView countDownTV;
    private Button btnPause;
    private Button btnEndSession;

    private long totalTime;
    private long taskTime;
    private long elapsedTime;
    private long sessionTime;
    private boolean active;
    private String taskTitle;
    private int taskIndex;
    private Task currentTask;

    String TAG = "StudyTimeActivity";
    StudyManagement management; // General StudyManagement object to store app information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        // getting the task name and the task Index from the previous activity
        taskTitle = intent.getStringExtra("task");
        taskIndex = intent.getIntExtra("taskIndex",0);

        management = (StudyManagement) getApplicationContext();

        Log.d(TAG,"storage :" + management.toString());
        currentTask = management.getTask(taskIndex);

        totalTime = management.getTotalTime();

        setContentView(R.layout.activity_study_time);

        lblTaskTitle = findViewById(R.id.lblTaskTitle);
        String screenTitle = lblTaskTitle.getText().toString() + " " + taskTitle;
        lblTaskTitle.setText(screenTitle);

        taskTime = currentTask.getTotalTime() - currentTask.getSpentTime();

        Log.d(TAG,"time remaining :" + totalTime);
        countDownTV = findViewById(R.id.countDown);
        sessionTimeTV = findViewById(R.id.recordedTime);
        totalTimeTV = findViewById(R.id.totalTime);
        totalTimeTV.setText(TimeConvert.convertSecondsToTime(totalTime));

        // Time is in millisecond so 50sec = 50000 I have used
        // countdown Interval is 1sec = 1000 I have used
        countDown = new CountDownTimer(taskTime*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                elapsedTime = millisUntilFinished;
                countDownTV.setText(TimeConvert.convertMillisToTime(millisUntilFinished));
                sessionTimeTV.setText(TimeConvert.convertMillisToTime(taskTime*1000 - millisUntilFinished));
            }
            // When the task is over it will print 00:00:00 there
            @Override
            public void onFinish() {
                countDownTV.setText("00:00:00");
            }
        };
        active = true;
        countDown.start();

        btnPause = findViewById(R.id.btnPause);
        btnPause.setOnClickListener(this);

        btnEndSession = findViewById(R.id.btnEndSession);
        btnEndSession.setOnClickListener(this);

    }



    public void onClick(View v)
    {
        if (v == btnEndSession) {
            if (active) {
                countDown.cancel();
            }
            Intent myIntent = new Intent(StudyTimeActivity.this, AddTaskLogActivity.class);
            myIntent.putExtra("task", taskTitle);
            myIntent.putExtra("taskIndex", taskIndex);
            myIntent.putExtra("startTime", taskTime*1000);
            myIntent.putExtra("sessionTime", taskTime*1000 - elapsedTime);
            StudyTimeActivity.this.startActivity(myIntent);
        }
        else if (v == btnPause){
            if (active) {
                countDown.cancel();
                Log.d("Elapsed Time", Long.toString(elapsedTime));
                active = false;
            }
            else {
                // Time is in millisecond so 50sec = 50000 I have used
                // countdown Interval is 1sec = 1000 I have used
                countDown = new CountDownTimer(elapsedTime,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        elapsedTime = millisUntilFinished;
                        countDownTV.setText(TimeConvert.convertMillisToTime(millisUntilFinished));
                        sessionTimeTV.setText(TimeConvert.convertMillisToTime(taskTime*1000 - millisUntilFinished));
                    }
                    // When the task is over it will print 00:00:00 there
                    @Override
                    public void onFinish() {
                        countDownTV.setText("00:00:00");
                    }
                };
                countDown.start();
                active = true;
            }
        }
    }
}