package com.example.finalcpn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//public class history extends AppCompatActivity {
//    private FirebaseAuth mAuth;
//
//    private RecyclerView mRecyclerView;
//    private ImagePredictionAdapte mAdapter;
//    private List<ImagePrediction> mImagePredictionList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_history);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        mRecyclerView = findViewById(R.id.recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mImagePredictionList = new ArrayList<>();
//        mAdapter = new ImagePredictionAdapte(this, mImagePredictionList);
//        mRecyclerView.setAdapter(mAdapter);
//
//        String userId = mAuth.getCurrentUser().getUid();
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
//        userRef.child("images").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mImagePredictionList.clear();
//                for (DataSnapshot imageSnapshot : snapshot.getChildren()) {
//                    ImagePrediction imagePrediction = imageSnapshot.getValue(ImagePrediction.class);
//                    mImagePredictionList.add(imagePrediction);
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle any errors that occur during the database operation
//            }
//        });
//    }
//}
//
//
//
//


public class history extends AppCompatActivity {
    private  FirebaseAuth mAuth;


    private RecyclerView RecyclerView;
    private PredictionAdapter Adapter;
    private List<predictionModel> predictionlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mAuth = FirebaseAuth.getInstance();
    getSupportActionBar().hide();
        RecyclerView = findViewById(R.id.recycler_view);

/// RecyclerView ka instance retrieve karna aur use LinearLayoutManager ke saath set karna

        RecyclerView.setLayoutManager(new LinearLayoutManager(this));

// Prediction model list ko initialise karna

        predictionlist = new ArrayList<>();

        // PredictionAdapter ka instance banakar usko RecyclerView se attach karna
        Adapter = new PredictionAdapter(this, predictionlist);

        RecyclerView.setAdapter(Adapter);

        // Current user ka UID retrieve karna
        String userId = mAuth.getCurrentUser().getUid();

//        Firebase Realtime Database ka reference retrieve karna
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        // "images" node ke andar jo data hai, usko retrieve karna

        userRef.child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                predictionlist.clear();
                // Sabhi "images" nodes ke andar ke predictions ko retrieve karna aur prediction model list mei add karna
                for (DataSnapshot imageSnapshot : snapshot.getChildren()) {
                    predictionModel predictionModel = imageSnapshot.getValue(predictionModel.class);
                    predictionlist.add(predictionModel);
                }
                // Adapter ko notify karna ki data set mei changes hue hai aur view ko refresh karna
                Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(history.this, "no data found", Toast.LENGTH_SHORT).show();

            }
        });
    }



 }




