package com.example.app_v1.apiclients;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class FirebaseUserInterceptor implements Interceptor {

    private static final String X_FIREBASE_ID_TOKEN = "firebaseUserId";
    private static final String TAG = "FirebaseUserInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                Log.d(TAG, "intercept: not logged in");
                throw new Exception("User not logged in");
            } else {
                Task<GetTokenResult> task = user.getIdToken(true);
                GetTokenResult tokenResult = Tasks.await(task);
                String idToken = tokenResult.getToken();
                Log.d(TAG, "intercept: token=" + idToken);

                if (idToken == null) {
                    Log.d(TAG, "intercept: idToken null");
                    throw new Exception("idToken null");
                } else {
                    Request modifiedRequest = request.newBuilder()
                            .addHeader(X_FIREBASE_ID_TOKEN, idToken)
                            .build();
                    return chain.proceed(modifiedRequest);
                }
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
