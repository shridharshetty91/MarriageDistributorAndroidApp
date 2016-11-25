package com.example.shridhar.distributorapp.HelperComponents;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.shridhar.distributorapp.MyApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Shridhar on 10/25/16.
 */

public class HelperFunctions {
    public static String getLogTAG() {
        return MyApp.getContext().getPackageName();
    }

    public static String getPackageName() {
        return MyApp.getContext().getPackageName();
    }

    public static String getStorageFolderePath() {
        return Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getPackageName();
    }
    public static String getStorageFolderePathForImages() {
        return getStorageFolderePath() + "/Images/";
    }

    public static String writeImageToFile(Bitmap image) {
        File mediaStorageDir = new File(getStorageFolderePathForImages());

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                mediaStorageDir.delete();
                if (! mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
        }
        // Create a media file name

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File pictureFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        pictureFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 70, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(getLogTAG(), "File not found: " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.d("", "Error accessing file: " + e.getMessage());
            return null;
        }

        return pictureFile.getPath();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
