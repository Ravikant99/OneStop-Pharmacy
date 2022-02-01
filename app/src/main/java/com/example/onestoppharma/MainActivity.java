package com.example.onestoppharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.onestoppharma.databinding.ActivityOtpSendBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView userMobile;
    Button userLogout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userMobile = findViewById(R.id.user_mobile);
        userLogout = findViewById(R.id.btn_logout);

           firebaseAuth = FirebaseAuth.getInstance();
           firebaseUser = firebaseAuth.getCurrentUser();

         userMobile.setText(firebaseUser.getPhoneNumber());

         userLogout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 FirebaseAuth.getInstance().signOut();
                 Intent intent = new Intent(MainActivity.this,OtpSendActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 startActivity(intent);
                 finish();
             }

         });


    }
}