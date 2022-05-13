package com.codecompany.currentc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    private final String LOG_TAG = LoginPage.class.getSimpleName();
    private EditText mEmail, mPassword;
    private TextView mRegister;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.et_Email);
        mPassword = findViewById(R.id.et_Password);
        Button mSignIn = findViewById(R.id.b_SignIn);
        mRegister = findViewById(R.id.tv_Register);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "User is already logged in, " + user.getEmail());
            startActivity(new Intent(LoginPage.this, MainActivity.class));
        }

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Calling sign in user");
                signIn();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Switching to register page");
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
                finish();
            }
        });
    }

    private void signIn() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Log.d("Shakir", "Logged in Successfully");
                            Toast.makeText(LoginPage.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginPage.this, MainActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_LONG).show();
                        Log.d("Shakir", e.getMessage());
                        e.printStackTrace();
                    }
                });
            }else {
                mPassword.setError("Empty response");
            }
        }else {
            mEmail.setError("Invalid response");
        }
    }
}