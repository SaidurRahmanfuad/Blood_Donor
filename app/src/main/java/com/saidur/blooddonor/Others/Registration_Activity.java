package com.saidur.blooddonor.Others;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;
import com.saidur.blooddonor.R;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.saidur.blooddonor.View_activity.Homes_main;

import java.util.concurrent.TimeUnit;

public class Registration_Activity extends AppCompatActivity {
    private EditText otpnumber,user;
    private Button button_sendcode;
    private String userName,numberAuth,password,confrmPassword;
    private FirebaseAuth firebaseAuth;
    private String mVerificationId;
    private String code= null;
    private String mobileNumber = null;
   // private List<DonorOnline> firebaseId = new ArrayList<>();
    private CountryCodePicker countryCodePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);

        init();
        onClick();

    }

    private void onClick()
    {
        button_sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userName  = user.getText().toString();
                numberAuth = otpnumber.getText().toString().trim();
              /*  password = pass.getText().toString();
                confrmPassword = confirm.getText().toString();*/

                // String code = user.getText().toString().trim();

                if(code == null && mobileNumber == null)
                {

                    if(numberAuth.isEmpty() )
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Registration_Activity.this);
                        builder.setMessage("Enter the Verification number ");
                        builder.show();

                    }
                    else
                    {
                        sendVerificationCode(numberAuth);

                        if(code != null)
                        {
                            confirmClear();
                        }

                    }
                }
                else if (code == null && numberAuth.length() < 6) {
                    user.setError("Enter valid code");
                    user.requestFocus();
                    return;
                } else if(code == null && mobileNumber != null)
                {
                    verifyVerificationCode(numberAuth);
                }


            }
        });
    }

    private void confirmClear() {
        button_sendcode.setText("Confirm");

    /*  CardView cardView1= findViewById(R.id.cardView1),cardView3= findViewById(R.id.cardView3),cardView4= findViewById(R.id.cardView4);
        cardView1.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        cardView4.setVisibility(View.GONE);*/

        countryCodePicker.setVisibility(View.GONE);

        otpnumber.setText(null);
        otpnumber.setHint("Verify Code");
    }

    @Override
    protected void onStart() {
        super.onStart();

        String  id = null;
        try {
            id = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }catch (Exception e)
        {

        }
        Log.d("id", "onStart: "+id);
        if(id != null)
        {
            startActivity(new Intent(Registration_Activity.this, Homes_main.class));
            finish();
        }
    }

    private void init()
    {
        //  user = findViewById(R.id.userdSingUpETId);

        countryCodePicker = findViewById(R.id.cP);
        countryCodePicker.setCountryForPhoneCode(+880);
        otpnumber = findViewById(R.id.otp_number);

        /*  pass = findViewById(R.id.passSingUpETId);
        confirm = findViewById(R.id.confirmSingUpETId);*/

        button_sendcode = findViewById(R.id.otp_sendcode);
        firebaseAuth = FirebaseAuth.getInstance();


    }



    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+countryCodePicker.getSelectedCountryCode() + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);

        mobileNumber = mobile;
        confirmClear();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null)
            {
                otpnumber.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Registration_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }

    };


    private void verifyVerificationCode(String code)
    {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Registration_Activity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {

                            // Common.phone_number=number;
                            // User user =new User(number,getIntent().getStringExtra("name"),getIntent().getStringExtra("password"));
                            FirebaseDatabase.getInstance().getReference("User");
                            Intent intent = new Intent(Registration_Activity.this, Creates_profile.class);
                            intent.putExtra("number",getIntent().getStringExtra("number"));
                            startActivity(intent);
                            finish();




                        }
                        else
                        {
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(Registration_Activity.this,""+message,Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }


}


