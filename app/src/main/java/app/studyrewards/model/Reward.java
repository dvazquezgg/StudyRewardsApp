package app.studyrewards.model;

import java.io.Serializable;

/**
 * Rewards class to represent available rewards to obtain after completing study time
 * implements Serializable to allow storing information in files
 */
public class Reward implements Serializable {

    /*
    * Reward description as text. Will be shown in list
    * */
    private String description;

    /*
    * Flag to disable reward when obtained
    * */
    private boolean obtained;

    public Reward(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isObtained() {
        return obtained;
    }

    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "description='" + description + '\'' +
                '}';
    }
}
