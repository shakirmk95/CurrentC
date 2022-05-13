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

public class RegisterPage extends AppCompatActivity {

    private final String LOG_TAG = RegisterPage.class.getSimpleName();
    private EditText mRegEmail, mRegPassword;
    private TextView mSignIn;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegEmail = findViewById(R.id.et_Email);
        mRegPassword = findViewById(R.id.et_Password);
        Button mRegister = findViewById(R.id.b_SignIn);
        mSignIn = findViewById(R.id.tv_Register);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "User is already logged in, " + user.getEmail());
            startActivity(new Intent(RegisterPage.this, MainActivity.class));
            finish();
        }

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Calling create user");
                createUser();
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Switching to login page");
                startActivity(new Intent(RegisterPage.this, LoginPage.class));
            }
        });
    }

    private void createUser () {
        String email = mRegEmail.getText().toString();
        String password = mRegPassword.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterPage.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterPage.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterPage.this, "Registered Failed", Toast.LENGTH_LONG).show();
                        Log.d("Shakir", e.getMessage());
                        e.printStackTrace();
                    }
                });
            }else {
                mRegPassword.setError("Empty response");
            }
        }else {
            mRegEmail.setError("Invalid response");
        }
    }
}