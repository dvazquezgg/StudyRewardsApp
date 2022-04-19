package app.studyrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import app.studyrewards.model.StudyManagement;
import app.studyrewards.persistance.CSVReader;
import app.studyrewards.persistance.FileStorageAndroid;

/**
 * SetupActivity activity to read rewards from the file and select a reward to acquire
 * extends AppCompatActivity as a default activity in Android
 * implements View.OnClickListener to respond to buttons
 */
public class SetupActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner;
    private Button btnRewards;
    private TextView selectedRewardTV;

    private String selectedReward;

    String TAG = "SetupActivity";
    private StudyManagement studyManagement; // General StudyManagement object to store app information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        selectedRewardTV = findViewById(R.id.selectedRewardTV);
        selectedReward = "";
        spinner = findViewById(R.id.spinnerRewards);

        CSVReader fileReader = new CSVReader(this); // calling the constructor
        List<String> rewardsFromCSV = fileReader.readRewardsFromCSV();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SetupActivity.this,
                android.R.layout.simple_spinner_item,rewardsFromCSV);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnRewards = findViewById(R.id.btnRewards);
        btnRewards.setOnClickListener(this);

        studyManagement = (StudyManagement) getApplicationContext();
        StudyManagement.loadFromDisk(getApplicationContext(),studyManagement); // Establish object to save data

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        selectedReward = (String) parent.getItemAtPosition(position);
        selectedRewardTV.setText(selectedReward);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    public void onClick(View v)
    {
        if (v == btnRewards) {
            studyManagement.insertReward(selectedReward);
            Intent myIntent = new Intent(SetupActivity.this, ReviewTaskActivity.class);
            SetupActivity.this.startActivity(myIntent);
        }
    }
}