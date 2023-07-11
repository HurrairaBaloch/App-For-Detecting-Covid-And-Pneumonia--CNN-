package com.example.finalcpn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;
    public static final String API_URL = " http://192.168.83.162:5000/predict";

    private ImageView Image;
    private  Button history;
    private String predictedClass;

//    predictionModel predictionModel =new predictionModel();
    private Button btnPredict;
    private ImageButton signout;
    private Button care;

    //      Button buttonpred;
    private TextView tvPrediction;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    CardView cardView;
    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//  getSupportActionBar().hide();


//        showing users  previous data
        history=findViewById(R.id.history);
care=findViewById(R.id.showprecautions);


care.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, PrecautionsActivity.class);
        intent.putExtra("predictedClass", predictedClass);
        startActivity(intent);

    }
});

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,history.class);
                startActivity(intent);
            }
        });




        mAuth=FirebaseAuth.getInstance();

        cardView = findViewById(R.id.cardView);

//        toolbar as action bar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        backbutton

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Image = findViewById(R.id.iv_image);
        btnPredict = findViewById(R.id.btn_predict);
        tvPrediction = findViewById(R.id.tv_prediction);
        signout = findViewById(R.id.btn_signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                mAuth.signOut();
                showDialog();

            }
        });
        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });
    }





    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select a Chest-Xray");
        builder.setIcon(R.drawable.twotone_image_24);
        //an array of string
        builder.setItems(new String[]{"Take Picture", "Choose from Gallery"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:


                                // Take Picture
//                                MediaStore is a class in the Android SDK that provides
//                                    access to various types of media content,
//                                    such as images, videos, and audio files
//                                default camra app will open as there is no chooser that this action type apps
//                                can be open from this action
//                                requet code will identify the specific req call
//                                result code is for that the data is correct or not
//Result ok mean that the result is all ok
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAMERA);

                                break;

                            case 1:

                                // Choose from Gallery
                                Intent galleryIntent = new Intent();
                                galleryIntent.setType("image/*");
                                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), REQUEST_IMAGE_GALLERY);
                                break;
                        }
                    }
                });
        builder.show();
    }



//    Bitmap is a class in Android that represents a graphic image.
//    It is a type of object that holds the image pixel data in memory.
//    In Android, images are stored as bitmap files, which are made
//    up of pixels arranged in a grid pattern.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                //uri will identify the image resource
                Uri imageUri = data.getData();
                try {
//
                    //                                MediaStore is a class in the Android SDK that provides
//                                    access to various types of media content,
//                                    such as images, videos, and audio files
//
//                                requet code will identify the specific req call
//                                result code is for that the data is correct or not

                    Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 710, 600, false);
                    Image.setImageBitmap(resizedBitmap);
                    predictImage(resizedBitmap);
                    ///if i want on btn
                    btnPredict.setText("Upload Again");
//

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_CAMERA) {
                //                static way return an object this line return an object of bitmap data

                Bitmap originalBitmap = (Bitmap) data.getExtras().get("data");
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 710, 600, false);
                Image.setImageBitmap(resizedBitmap);
                predictImage(resizedBitmap);

                btnPredict.setText("Preiction");
            }
        }
    }


    private void predictImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

        // Step 1: Send image to server
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image.png", RequestBody.create(MEDIA_TYPE_PNG, imageBytes))
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Content-Type", "multipart/form-data")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPrediction.setText("Failed to get prediction");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                try {
                    JSONObject json = new JSONObject(responseData);
                    String predictedClass = json.getString("prediction");
                    String maxProb = json.getString("max_prob");

                    // Step 2: Upload image to Firebase Storage
                    StorageReference imageRef = storageRef.child("images/" + System.currentTimeMillis() + ".png");
                    UploadTask uploadTask = imageRef.putBytes(imageBytes);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Step 3: Save image URL and prediction to Realtime Database
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    String userId = mAuth.getCurrentUser().getUid();
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
                                    String imageId = userRef.child("images").push().getKey();
                                    userRef.child("images").child(imageId).setValue(new predictionModel(imageUrl, predictedClass));
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String predictionText = "Prediction: " + predictedClass + "\nProbability: " + maxProb;
                            tvPrediction.setText(predictionText);
                            cardView.setVisibility(View.VISIBLE);
                            care.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvPrediction.setText(responseData);
                            Toast.makeText(MainActivity.this, "Selct a chest x ray image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void showMessageAndExitApp() {
        View toastLayout = getLayoutInflater().inflate(R.layout.toast_layout, null);
        TextView toastMessage = toastLayout.findViewById(R.id.custom_toast_message);
        toastMessage.setText("Thank you for choosing us!");

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastLayout);
        toast.show();

        finishAffinity();
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?");

               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Perform logout action here
                        mAuth.signOut();
                        showMessageAndExitApp();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
        builder.show();
    }



}