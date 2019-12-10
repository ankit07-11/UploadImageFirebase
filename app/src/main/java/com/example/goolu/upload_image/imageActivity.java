package com.example.goolu.upload_image;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class imageActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private DatabaseReference mdatabaseRef;
    private List<upload> mupload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mRecyclerView =findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mupload=new ArrayList<>();
        mdatabaseRef=FirebaseDatabase.getInstance().getReference("upload");
        mdatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot :dataSnapshot.getChildren()){
                    upload uploads=postSnapshot.getValue(upload.class);
                    mupload.add(uploads);

                }
                mAdapter=new Adapter(imageActivity.this,mupload);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(imageActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
