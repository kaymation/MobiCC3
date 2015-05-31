package com.mobiquity.kevinq.mobicc3;

/**
 * Created by Kevin Quigley on 5/29/2015.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

        import com.dropbox.client2.DropboxAPI;
        import com.dropbox.client2.DropboxAPI.Entry;
        import com.dropbox.client2.exception.DropboxException;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Adapter;
import android.widget.GridView;
import javax.imageio.*;

public class ListFiles extends ActionBarActivity implements OnClickListener {

    private DropboxAPI dropboxApi;
    private String path;
    private Handler handler;

    public ListFiles(DropboxAPI dropboxApi, String path, Handler handler) {
        super();
        this.dropboxApi = dropboxApi;
        this.path = path;
        this.handler = handler;
    }

    public class Viewtry {
        public Entry entry;
        public Bitmap bitmap;

        public Viewtry(Entry e, Bitmap b){
            entry = e;
            bitmap = b;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        GridView grid = (GridView) findViewById(R.id.gridview);
        ArrayList<Viewtry> pairs = new ArrayList<Viewtry>();

        try {
            Entry directory = dropboxApi.metadata(path, 1000, null, true, null);
            for(Entry entry : directory.contents) {
                DropboxAPI.DropboxInputStream dis = dropboxApi.getThumbnailStream(entry.path, DropboxAPI.ThumbSize.ICON_256x256, DropboxAPI.ThumbFormat.JPEG);
                pairs.add(new Viewtry(entry,BitmapFactory.decodeStream(dis)) );
            }
        } catch (DropboxException e) {
            /* todo write exceptions */
        } catch (Exception e){
            /* todo write exceptions */
        }

        ImageAdapter imgAdpt = new ImageAdapter(this, R.id.gridview, (Viewtry[])pairs.toArray() );
        grid.setAdapter(imgAdpt);
        setContentView(R.layout.files_list);

    }

    public void onClick(View v){
        if(v.getTag(1)!= null) {
            String filepath = ((Entry) (v.getTag(1))).fileName();

            try {
                File file = new File("/", "tempfile");
                FileOutputStream outputStream = new FileOutputStream(file);
                DropboxAPI.DropboxFileInfo info = dropboxApi.getFile("/" + filepath, null, outputStream, null);
            } catch (DropboxException | FileNotFoundException e) {
            /* todo */
            }
            Intent displayFileIntent;
            String ext = filepath.substring(filepath.lastIndexOf('.') + 1);
            if (filepath.matches("jpeg")) {
                displayFileIntent = new Intent(this, DisplayPhotoActivity.class);

                startActivity(displayFileIntent);
            } else {
            }
        }
//        startActivity(displayFileIntent);




    }

//    @Override
//    protected void onPostExecute(ArrayList result) {
//        Message message = handler.obtainMessage();
//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList("data", result);
//        message.setData(bundle);
//        handler.sendMessage(message);
//    }
}