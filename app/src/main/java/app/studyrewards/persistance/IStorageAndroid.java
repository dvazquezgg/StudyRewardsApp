package app.studyrewards.persistance;

import android.content.Context;

import java.io.IOException;

import app.studyrewards.model.StudyManagement;

public interface IStorageAndroid {
    public void updateManagementData(Context context, StudyManagement management) throws IOException;
    public StudyManagement getManagementData(Context context) throws IOException;
}
