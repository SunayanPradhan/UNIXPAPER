package com.sunayanpradhan.unixpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView nature;
    ImageView wildlife;
    ImageView celebrity;
    ImageView event;
    ImageView sport;
    RecyclerView recyclerView;
    ProgressBar loading;
    ArrayList<String> list= new ArrayList<>();
    WallpaperAdapter adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.recyclerView);
        nature= findViewById(R.id.nature);
        wildlife=findViewById(R.id.wildlife);
        celebrity=findViewById(R.id.celebrity);
        event=findViewById(R.id.event);
        sport=findViewById(R.id.sport);
        loading=findViewById(R.id.loading);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
               adapter= new WallpaperAdapter(list,MainActivity.this);
               recyclerView.setAdapter(adapter);
               reference= FirebaseDatabase.getInstance().getReference("images").child("home");
               //recyclerView.setHasFixedSize(true);
               getData();



    nature.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "NATURE", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(MainActivity.this,NatureActivity.class);
            startActivity(intent);
        }
    });
        wildlife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "WILDLIFE", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this,WildlifeActivity.class);
                startActivity(intent);
            }
        });
        celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "CELEBRITY", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this,CelebrityActivity.class);
                startActivity(intent);
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "EVENT", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this,EventActivity.class);
                startActivity(intent);
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "SPORT", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this,SportActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot shot: snapshot.getChildren()) {
                    String data=shot.getValue().toString();
                    list.add(data);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter= new WallpaperAdapter(list,MainActivity.this);
                recyclerView.setAdapter(adapter);
                loading.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}