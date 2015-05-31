package com.mobiquity.kevinq.mobicc3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.dropbox.client2.DropboxAPI;

import java.io.File;

/**
 * An activity for Uploading photoes
 * Created by Kevin Quigley on 5/30/2015.
 */
public class UploadPhoto extends UploadFile {

    public static final int MEDIA_TYPE_IMAGE = 1;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;

    public UploadPhoto(Context context, DropboxAPI dropboxApi, String path) {
        super(context, dropboxApi, path);
    }

    public File doCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        File file = new File(getPath(), "tempphoto.txt");
        return file;
    }


}
