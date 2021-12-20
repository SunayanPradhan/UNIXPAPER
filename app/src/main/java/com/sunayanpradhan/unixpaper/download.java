package com.sunayanpradhan.unixpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class download extends AppCompatActivity {
    TextView download_title;
    ImageView download_image;
    ImageButton share;
    ImageButton save;
    ImageButton set;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_download);
        download_title= findViewById(R.id.download_title);
        download_image= findViewById(R.id.download_image);
        share= findViewById(R.id.share);
        save= findViewById(R.id.save);
        set= findViewById(R.id.set);
        Intent intent=getIntent();
         url= intent.getStringExtra(WallpaperAdapter.PHOTO);
        Glide.with(this).load(getIntent().getStringExtra(WallpaperAdapter.PHOTO)).into(download_image);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                BitmapDrawable drawable= (BitmapDrawable)download_image.getDrawable();
                Bitmap bitmap=drawable.getBitmap();
                File file= new File(getExternalCacheDir()+"/"+getResources().getString(R.string.app_name)+".png");
                Intent shareInt;
                try {
                    FileOutputStream outputStream=new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                    outputStream.flush();
                    outputStream.close();
                    shareInt= new Intent(Intent.ACTION_SEND);
                    shareInt.setType("image/*");
                    shareInt.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
                    shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(shareInt,"Share"));
                    Toast.makeText(download.this, "Successfully Share", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Toast.makeText(download.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }



            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(url);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    downloadManager.enqueue(request);
                    Toast.makeText(download.this, "Successfully Downloaded", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(download.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Bitmap bitmap=((BitmapDrawable)download_image.getDrawable()).getBitmap();
                    WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                    manager.setBitmap(bitmap);
                    Toast.makeText(download.this, "Successfully Set", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(download.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

    }
}