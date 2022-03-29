package app.studyrewards.persistance;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    Context context;
    final private String rewards_file = "rewards.csv";

    public CSVReader(Context context) {
        this.context = context;
    }

    /**
     * This method reads the file assigned to the object and returns a String[] array
     * @return String[]
     */
    public List<String> readRewardsFromCSV(){
        ArrayList<String> rewardNames = new ArrayList<>();
        /*
        * Load the csv file using an InputStream object
        * create a BufferedReader from the InputStream
        * */
        try {
            InputStream inputStream = context.getAssets().open(rewards_file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader csvReader = new BufferedReader(inputStreamReader);
            String row;
            int rowNum = 0;
            // read all lines
            while ((row = csvReader.readLine()) != null) {
                // Add data to the List
                rewardNames.add(row);  // Add the name of the reward
            }
            csvReader.close();
        } catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("Error" + ioe.getMessage());
        }
        return rewardNames;
    }

}
