package com.mobiquity.kevinq.mobicc3;

/**
 * Created by Kevin Quigley on 5/29/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFile extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private DropboxAPI dropboxApi;
    private String path;
    private Context context;

    public UploadFile(Context context, DropboxAPI dropboxApi, String path) {
        super();
        this.dropboxApi = dropboxApi;
        this.path = path;
        this.context = context;
    }

    /**
     * Create a file Uri for saving an image or video
     */
    protected static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    protected static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            File file = this.doCapture();//will be unique for every child
            FileInputStream fileInputStream = new FileInputStream(file);
            dropboxApi.putFile(path + file.getName(), fileInputStream,
                    file.length(), null, null);
            file.delete();

            return;
        } catch (Exception e) {

//        } catch (IOException ioe) {
//
//        } catch (DropboxException de) {
        }
        return;
    }

    public String getPath() {
        return path;
    }

    public File doCapture() {
        return null;
    }


}
