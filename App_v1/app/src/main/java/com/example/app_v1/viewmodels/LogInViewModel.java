package com.example.app_v1.viewmodels;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.app_v1.models.FirebaseModel;

public class LogInViewModel extends ViewModel {

    private FirebaseModel mFirebaseModel;

    public void init() {
        if (mFirebaseModel != null) return;
        mFirebaseModel = new FirebaseModel();
    }

    public FirebaseModel getMFireBaseModel() {
        return mFirebaseModel;
    }
    public String geStringFromTextView(TextView textView)
    {
        String s = textView.getText().toString();
        return s;
    }
    public boolean checkEmptyTextField(TextView textView)
    {
        return TextUtils.isEmpty(textView.getText().toString());
    }


}
