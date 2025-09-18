package com.example.fixnflex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class logout extends AppCompatActivity {
    Button logout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logout);

        logout=(Button) findViewById(R.id.logout);
        firebaseAuth=FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("rememberMe", false);
                editor.apply();

                firebaseAuth.signOut();
                Intent i1=new Intent(logout.this, MainActivity.class);
                startActivity(i1);
                finish();
            }
        });

    }
}