package com.sunayanpradhan.unixpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NatureActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar loading;
    ArrayList<String> list= new ArrayList<>();
    WallpaperAdapter adapter;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);
        recyclerView= findViewById(R.id.recyclerNature);
        loading=findViewById(R.id.loading);

        recyclerView.setLayoutManager(new LinearLayoutManager(NatureActivity.this));
        adapter= new WallpaperAdapter(list,NatureActivity.this);
        recyclerView.setAdapter(adapter);
        reference= FirebaseDatabase.getInstance().getReference("images").child("home");
        //recyclerView.setHasFixedSize(true);
        getData();
    }
    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot shot: snapshot.getChildren()) {
                    String data=shot.getValue().toString();
                    list.add(data);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(NatureActivity.this));
                adapter= new WallpaperAdapter(list,NatureActivity.this);
                recyclerView.setAdapter(adapter);
                loading.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loading.setVisibility(View.GONE);

                Toast.makeText(NatureActivity.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}