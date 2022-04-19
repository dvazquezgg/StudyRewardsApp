package app.studyrewards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.studyrewards.model.StudyManagement;
import app.studyrewards.model.Task;
import app.studyrewards.persistance.FileStorageAndroid;

/**
 * AddTaskActivity activity to create a new study task including the deadline
 * extends AppCompatActivity as a default activity in Android
 * implements View.OnClickListener to respond to buttons
 */
public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textTaskTitle;
    private EditText textTotalTime;
    private CalendarView cvDeadline;
    private TextView tvSpentTime;
    private TextView tvDeadline;
    private Button btnAddTask;

    private Date deadline;
    private int totalTime;

    String TAG = "AddTaskActivity";
    StudyManagement management; // General StudyManagement object to store app information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        management = (StudyManagement) getApplicationContext();

        textTaskTitle = (EditText) findViewById(R.id.textTaskTitle);
        textTotalTime = (EditText) findViewById(R.id.textHoursRequired);
        tvDeadline = (TextView) findViewById(R.id.tvDeadline);

        cvDeadline = (CalendarView)  findViewById(R.id.cvDeadline);
        CalendarView.OnDateChangeListener listener = new CalendarListener(tvDeadline);
        cvDeadline.setOnDateChangeListener(listener);
        cvDeadline.setDate((new Date()).getTime());

        btnAddTask = findViewById(R.id.btnAddTaskDetail);
        btnAddTask.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if (v == btnAddTask) {
            deadline = getDate(tvDeadline.getText().toString());
            totalTime = getTotalTime(textTotalTime.getText().toString());
            if(deadline != null && totalTime > 0){ // if you have a date selected and a Time
                Task task = new Task( textTaskTitle.getText().toString(),
                        deadline,
                        totalTime);
                management.setTask(task);
                management.saveData();
                Intent myIntent = new Intent(AddTaskActivity.this, ReviewTaskActivity.class);
                AddTaskActivity.this.startActivity(myIntent);
            }
            else {
                Toast.makeText(this,"Select a date for the task",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Converting total time to integer
     * @param totalTimeStr
     * @return
     */
    public int getTotalTime(String totalTimeStr){
        int totalTime = 0;
        try {
            totalTime = Integer.parseInt(totalTimeStr);
        } catch (NumberFormatException nfe){
            Toast.makeText(this,"Invalid number",Toast.LENGTH_SHORT).show();
        }
        return totalTime;
    }

    /**
     * Converting a String into a Date with SimpleDateFormat
     * @param dateString
     * @return
     */
    public Date getDate(String dateString) { // format dd-MM-yyyy
        Date deadline = null;
        try {
            deadline = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
        } catch (ParseException pe){
            Toast.makeText(this,"Invalid Date",Toast.LENGTH_SHORT).show();
        }
        return deadline;
    }

}

/**
 * Inner class to serve as a listener for the Calendar Control
 * Scaffolding from Android developers
 */
class CalendarListener implements CalendarView.OnDateChangeListener {

    TextView textView;

    CalendarListener(TextView textView){
        this.textView = textView;
    }
    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth){
        String date = dayOfMonth + "-" + (month + 1) + "-" + year;
        this.textView.setText(date);
    }

}