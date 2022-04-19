package app.studyrewards.persistance;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import app.studyrewards.model.StudyManagement;

/**
 * Implementation of IStorage interface for Android
 * From our initial prototype we created an interface to read and write
 * values from the General application object using serialization
 */
public class FileStorageAndroid {

    final static String DATA_FILE = "study_management.data";

    /**
     * Writes the current object into a data file into the device's file system
     * @param context
     * @param management
     * @throws IOException
     */
    public static void updateManagementData(Context context, StudyManagement management) throws IOException {
        try {
            File file = new File(context.getFilesDir(), DATA_FILE);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            // We use Serialization to convert the object into text to store into the file
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(management);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e){
            throw new IOException("Unable to access Application Data.");
        }
    }

    /**
     * Reads a file from device filesystem
     * @param context
     * @return
     * @throws IOException
     */
    public static StudyManagement getManagementData(Context context) throws IOException {
        try{
            File file = new File(context.getFilesDir(), DATA_FILE);
            FileInputStream fileInputStream = new FileInputStream(file);
            // We use Serialization to convert the object into text to store into the file
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            StudyManagement management = (StudyManagement) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return management;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            throw new IOException("File not Found");
        }
    }
}



