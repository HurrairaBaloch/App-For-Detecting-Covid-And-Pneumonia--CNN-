package com.example.finalcpn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.widget.EditText;
import android.view.View;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registraion extends AppCompatActivity {
    private EditText emailTextView, passwordTextView,name,number;
    private Button Btn;
        TextView btn2;
//
//    ImageView imageView;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
//    private Button gologin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion);

        getSupportActionBar().hide();

        btn2 = findViewById(R.id.btnregister);
        progressbar=findViewById(R.id.progress);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(registraion.this,login.class);
                startActivity(i);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        number=findViewById(R.id.number);
        name=findViewById(R.id.username);
        Btn = findViewById(R.id.bntlogin);


        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                registerNewUser();
            }
        });
    }

    private void registerNewUser()
    {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);


        // Take the value of two edit texts in Strings
        String username, email, password,numbers;
        username=name.getText().toString() ;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        numbers=number.getText().toString();

        // Get the text from the name and password fields

// Validate name and password length
        if (username.length() < 6) {
            name.setError("Name must be at least 6 characters long.");
            name.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (password.length() < 6) {
            passwordTextView.setError("Password must be at least 6 characters long.");
            passwordTextView.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTextView.setError("Please enter a valid email address.");
            emailTextView.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(username)) {
            name.setError("Name is required.");
            name.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }
        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            emailTextView.setError("Email is required.");
            emailTextView.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordTextView.setError("Password is required.");
            passwordTextView.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(numbers)) {
            passwordTextView.setError("Enter your  Number.");
            passwordTextView.requestFocus();
            progressbar.setVisibility(View.GONE);
            return;
        }


        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();
                            progressbar.setVisibility(View.GONE);

                            // hide the progress bar


                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(registraion.this,
                                    Selection.class);
                            registraion.this.startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);

                        }
                    }
                });


    }
}