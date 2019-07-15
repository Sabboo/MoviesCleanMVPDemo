package com.example.moviescleanmvpdemo.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.moviescleanmvpdemo.R;
import com.example.moviescleanmvpdemo.databinding.ActivityFacebookLoginBinding;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;

import org.json.JSONException;

import java.util.Arrays;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FacebookLoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private ActivityFacebookLoginBinding mBinding;
    private static final String EMAIL = "email";
    private static final String PROFILE = "public_profile";
    private static final String LOGOUT_MESSAGE = "User logged out successfully";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_facebook_login);

        if (isNetworkConnected())
            setupFacebookLogin();
        else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            mBinding.loginButton.setEnabled(false);
        }

        mBinding.buttonProceed.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        mBinding.loginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE));

        mBinding.loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                resetUserProfile();
                Toast.makeText(FacebookLoginActivity.this, LOGOUT_MESSAGE, Toast.LENGTH_SHORT).show();
            } else
                loadUserProfile(currentAccessToken);
        }
    };

    private void resetUserProfile() {
        mBinding.ivFbUserImage.setImageDrawable(null);
        mBinding.tvFbUserEmail.setText(null);
        mBinding.tvFbUserName.setText(null);
        mBinding.buttonProceed.setVisibility(GONE);
    }

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest graphRequest =
                GraphRequest.newMeRequest(newAccessToken, (object, response) -> {
                    try {
                        String firstName = object.getString("first_name");
                        String lastName = object.getString("last_name");
                        String email = object.getString("email");
                        String id = object.getString("id");
                        String image = "https://graph.facebook.com/" + id + "/picture?type=normal";

                        populateProfileData(firstName, lastName, email, image);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                });

        attachParametersAndExecute(graphRequest);
    }

    private void populateProfileData(String firstName, String lastName, String email, String image) {
        mBinding.tvFbUserName.setText(
                String.format("%s %s", firstName, lastName));
        mBinding.tvFbUserEmail.setText(email);

        Glide.with(this)
                .load(image)
                .into(mBinding.ivFbUserImage);

        mBinding.buttonProceed.setVisibility(VISIBLE);
    }

    private void attachParametersAndExecute(GraphRequest graphRequest) {
        Bundle params = new Bundle();
        params.putString("fields", "first_name,last_name,email,id");
        graphRequest.setParameters(params);
        graphRequest.executeAsync();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
