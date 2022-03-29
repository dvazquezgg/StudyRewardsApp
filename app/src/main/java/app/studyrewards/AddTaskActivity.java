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

import java.util.Date;

import app.studyrewards.model.StudyManagement;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddTask;

    private EditText textTaskTitle;
    private CalendarView cvDeadline;
    private EditText textTotalTime;
    private TextView tvSpentTime;
    private TextView tvDeadline;


    private String title;
    private Date deadline;
    private long totalTime;
    private long spentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final StudyManagement management = (StudyManagement) getApplicationContext();

        textTaskTitle = (EditText) findViewById(R.id.textTaskTitle);
        tvDeadline = (TextView) findViewById(R.id.tvDeadline);
        cvDeadline = (CalendarView)  findViewById(R.id.cvDeadline);
        CalendarView.OnDateChangeListener listener = new CalendarListener(tvDeadline);

        cvDeadline.setOnDateChangeListener(listener);
    }

    public void onClick(View v)
    {
        if (v == btnAddTask) {
            Intent myIntent = new Intent(AddTaskActivity.this, ReviewTaskActivity.class);
            AddTaskActivity.this.startActivity(myIntent);
        }
    }
}

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