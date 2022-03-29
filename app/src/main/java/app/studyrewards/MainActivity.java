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
import app.studyrewards.persistance.FileStorageAndroidImpl;
import app.studyrewards.persistance.IStorageAndroid;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private Spinner spinner;
    private Button btnRewards;
    private TextView selectedRewardTV;
    private String selectedReward;
    private final String USER_FILE = "user.data";

    private final String TAG = "SelectRewardActivity";

    private StudyManagement studyManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedRewardTV = findViewById(R.id.selectedRewardTV);
        selectedReward = "";
        spinner = findViewById(R.id.spinnerRewards);

        CSVReader fileReader = new CSVReader(this);
        List<String> rewardsFromCSV = fileReader.readRewardsFromCSV();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,rewardsFromCSV);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnRewards = findViewById(R.id.btnRewards);
        btnRewards.setOnClickListener(this);

        studyManagement = (StudyManagement) getApplicationContext();
        loadSavedStudyManagement(); // Establish object to save data
    }


    private void loadSavedStudyManagement(){
        StudyManagement management;
        // Attempt to read saved data
        try{
            IStorageAndroid storage = new FileStorageAndroidImpl(USER_FILE);
            management = storage.getManagementData(this);
            studyManagement.setRewards(management.getRewards());
            studyManagement.setTasks(management.getTasks());
            studyManagement.setTaskLogs(management.getTaskLogs());
            studyManagement.setProgress(management.getProgress());
            studyManagement.setTimer(management.getTimer());
        } catch (IOException ioe){
            // NO StudyManagement in files
            Log.w(TAG, "getStudyManagement: No Data saved found.",ioe);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the third item gets selected
                break;

        }
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
            Intent myIntent = new Intent(MainActivity.this, ReviewTaskActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
    }
}