package app.studyrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.studyrewards.model.StudyManagement;

public class ReviewTaskActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSelectedReward;
    Button btnAddTask;
    Button btnStudyTime;

    StudyManagement management;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_task);

        management = (StudyManagement) getApplicationContext();

        tvSelectedReward = findViewById(R.id.tvSelectedReward);
        tvSelectedReward.setText(management.getCurrentReward().getDescription());

        btnAddTask = findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(this);

        btnStudyTime = findViewById(R.id.btnStudyTime);
        btnStudyTime.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if (v == btnStudyTime) {
            Intent myIntent = new Intent(ReviewTaskActivity.this, StudyTimeActivity.class);
            ReviewTaskActivity.this.startActivity(myIntent);
        }
        else if (v == btnAddTask){
            Intent myIntent = new Intent(ReviewTaskActivity.this, AddTaskActivity.class);
            ReviewTaskActivity.this.startActivity(myIntent);
        }
    }
}