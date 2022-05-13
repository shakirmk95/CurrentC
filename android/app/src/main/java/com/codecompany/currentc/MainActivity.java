package com.codecompany.currentc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mSignOut = findViewById(R.id.b_LogOut);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            Log.d(LOG_TAG, "User is not logged in");
            Toast.makeText(MainActivity.this, "Unknown User", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, LoginPage.class));
            finish();
        }else {
            TextView name = findViewById(R.id.tv_Name);
            TextView email = findViewById(R.id.tv_Email);

            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
        }

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Signing out user");
                Toast.makeText(MainActivity.this, "Signing out", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginPage.class));
                finish();
            }
        });
    }
}