package com.mobiquity.kevinq.mobicc3;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Kevin Quigley on 5/31/2015.
 */
public class DisplayPhotoActivity extends DisplayFileActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        image = (ImageView) findViewById(R.id.image_space);
        File file = new File("/", "tempfile");
        image.setImageURI(Uri.fromFile(file));
        setContentView(R.layout.display_img);
    }
}
