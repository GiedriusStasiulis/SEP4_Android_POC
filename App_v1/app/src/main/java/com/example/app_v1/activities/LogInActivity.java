package com.example.app_v1.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_v1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private EditText mEmail;
    private EditText mPassword;
    private CheckBox mCheckBox;
    private Button buttonLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();

        // Views
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        //Buttons
        buttonLogIn =findViewById(R.id.log_in_button);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the checkbox preference
                if(mCheckBox.isChecked()){
                    //set a checkbox when the application starts
                    mEditor.putString(getString(R.string.check_box), "True");
                    mEditor.commit();

                    //save email
                    String email = mEmail.getText().toString();
                    mEditor.putString(getString(R.string.email), email);
                    mEditor.commit();
                }else{
                    //set a checkbox when the application starts
                    mEditor.putString(getString(R.string.check_box), "False");
                    mEditor.commit();

                    //save email
                    mEditor.putString(getString(R.string.email), "");
                    mEditor.commit();

                }
                logIn();
            }
        });

        mCheckBox = findViewById(R.id.checkBox);

        //Objects
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();

        checkSharedPreferences();
    }

    private void checkSharedPreferences()
    {

        String checkbox = mPreferences.getString(getString(R.string.check_box), "false");
        String email= mPreferences.getString(getString(R.string.email), "");

        mEmail.setText(email);

        if(checkbox.equals("True")){
            mCheckBox.setChecked(true);
        }
        else{
            mCheckBox.setChecked(false);
        }



    }

    private void updateUI(FirebaseUser user) {
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            goNextPage();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.log_in_button) {
            signIn(mEmail.getText().toString(), mPassword.getText().toString());
        }
    }

    public void logIn()
    {
        signIn(mEmail.getText().toString(), mPassword.getText().toString());
    }

    public void goNextPage(){
        Intent intent = new Intent(this, GreenhouseSelectActivity.class);
        startActivity(intent);
    }
}