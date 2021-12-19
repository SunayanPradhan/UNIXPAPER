package com.sunayanpradhan.unixpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class download extends AppCompatActivity {
    TextView download_title;
    ImageView download_image;
    ImageButton share;
    ImageButton save;
    ImageButton set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_download);
        download_title= findViewById(R.id.download_title);
        download_image= findViewById(R.id.download_image);
        share= findViewById(R.id.share);
        save= findViewById(R.id.save);
        set= findViewById(R.id.set);
        Glide.with(this).load(getIntent().getStringExtra(WallpaperAdapter.PHOTO)).into(download_image);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    try {
                        BitmapDrawable drawable = (BitmapDrawable)download_image.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,null, null);
                        Uri uri = Uri.parse(bitmapPath);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("image/png");
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.putExtra(Intent.EXTRA_TEXT, "UNIXPAPER");
                        startActivity(Intent.createChooser(intent, "Share"));
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