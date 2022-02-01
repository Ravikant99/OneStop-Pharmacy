package com.example.onestoppharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.onestoppharma.databinding.ActivityOtpSendBinding;
import com.example.onestoppharma.databinding.ActivityOtpSendBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpSendActivity extends AppCompatActivity {


    private ActivityOtpSendBinding binding;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpSendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();


        ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

          binding.getotpbtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(!binding.inputMobileNumber.getText().toString().isEmpty()){
                      if ((binding.inputMobileNumber.getText().toString().trim().length() == 10)) {
                              otpSend();
//                          progressBar.setVisibility(View.VISIBLE);
//                          binding.getotpbtn.setVisibility(View.INVISIBLE);
//
//                          PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                                  "+91" + binding.inputMobileNumber.getText().toString(),
//                                  60,
//                                  TimeUnit.SECONDS,
//                                  OtpSendActivity.this,
//                                  new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                      @Override
//                                      public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                          progressBar.setVisibility(View.GONE);
//                                          binding.getotpbtn.setVisibility(View.INVISIBLE);
//                                      }
//
//                                      @Override
//                                      public void onVerificationFailed(@NonNull FirebaseException e) {
//                                          progressBar.setVisibility(View.GONE);
//                                          binding.getotpbtn.setVisibility(View.VISIBLE);
//                                          Toast.makeText(OtpSendActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
//                                      }
//
//                                      @Override
//                                      public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                          progressBar.setVisibility(View.GONE);
//                                          binding.getotpbtn.setVisibility(View.VISIBLE);
//                                          Intent intent = new Intent(getApplicationContext(), OtpVerifyActivity.class);
//                                          intent.putExtra("mobile",binding.inputMobileNumber.getText().toString());
//                                          intent.putExtra("backendotp",backendotp);
//                                          startActivity(intent);
//                                      }
//                                  }
//
//                          );

                      }
                      else {
                          Toast.makeText(OtpSendActivity.this,"Please enter correct number",Toast.LENGTH_SHORT).show();
                      }
                  }
                  else {
                      Toast.makeText(OtpSendActivity.this,"Enter Mobile number",Toast.LENGTH_SHORT).show();
                  }
              }
          });
    }

    private void otpSend(){

        binding.progressbarSendingOtp.setVisibility(View.VISIBLE);
        binding.getotpbtn.setVisibility(View.INVISIBLE);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                binding.progressbarSendingOtp.setVisibility(View.GONE);
                binding.getotpbtn.setVisibility(View.VISIBLE);
                Toast.makeText(OtpSendActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                binding.progressbarSendingOtp.setVisibility(View.GONE);
                binding.getotpbtn.setVisibility(View.VISIBLE);
                Toast.makeText(OtpSendActivity.this,"OTP send successfully ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OtpSendActivity.this,OtpVerifyActivity.class);
                intent.putExtra("phone",binding.inputMobileNumber.getText().toString().trim());
                intent.putExtra("verificationId",verificationId);
                startActivity(intent);
            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+ binding.inputMobileNumber.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}
