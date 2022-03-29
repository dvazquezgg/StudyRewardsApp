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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import app.studyrewards.model.StudyManagement;

public class StudyTimeActivity extends AppCompatActivity implements View.OnClickListener {

    // Initializing my textView
    private TextView countDownTV;
    private Button btnGetTime;
    private Button btnStart;
    private Button btnStop;
    private Button btnGetProgress;
    private Button btnImages;
    private Chronometer simpleChronometer;
    private TextView totalTimeTV;
    private TextView recordedTimeTV;
    private long totalTime;

    String TAG = "StudyTimeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final StudyManagement management = (StudyManagement) getApplicationContext();

        Log.d(TAG,"storage :" + management.toString());

        setContentView(R.layout.activity_study_time);
        totalTime = 0;
        countDownTV = findViewById(R.id.countDown);
        recordedTimeTV = findViewById(R.id.recordedTime);
        totalTimeTV = findViewById(R.id.totalTime);

        // Time is in millisecond so 50sec = 50000 I have used
        // countdown Interval is 1sec = 1000 I have used
        CountDownTimer countDown = new CountDownTimer(50000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownTV.setText(convertMillisToTime(millisUntilFinished));
            }
            // When the task is over it will print 00:00:00 there
            @Override
            public void onFinish() {
                countDownTV.setText("00:00:00");
            }
        };
        countDown.start();

        simpleChronometer = (Chronometer) findViewById(R.id.chrono); // initiate a chronometer
        // simpleChronometer.start(); // start a chronometer


        btnGetTime = findViewById(R.id.btnGetTime);
        btnGetTime.setOnClickListener(this);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);

        btnGetProgress = findViewById(R.id.btnGetProgress);
        btnGetProgress.setOnClickListener(this);

        btnImages = findViewById(R.id.btnImages);
        btnImages.setOnClickListener(this);
    }

    private String convertMillisToTime(long milliseconds){
        NumberFormat f = new DecimalFormat("00");
        long hour = (milliseconds / 3600000) % 24;
        long min = (milliseconds / 60000) % 60;
        long sec = (milliseconds / 1000) % 60;
        String timeStr = f.format(hour) + ":" + f.format(min) + ":" + f.format(sec);
        return timeStr;
    }
    public void onClick(View v)
    {
        if (v == btnGetTime) {
            recordedTimeTV.setText(simpleChronometer.getText());
            long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
            totalTime = totalTime + elapsedMillis;

            totalTimeTV.setText(convertMillisToTime(totalTime));
        }
        else if (v == btnStart) {
            simpleChronometer.setBase(SystemClock.elapsedRealtime());
            simpleChronometer.start();
        }
        else if (v == btnStop) {
            simpleChronometer.stop();
        }
        else if (v == btnGetProgress){

        }
        else if (v == btnImages){

        }

    }
}