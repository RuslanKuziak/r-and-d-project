package co.techmagic.randd.presentation.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import co.techmagic.randd.R;
import co.techmagic.randd.presentation.main.MainActivity;

/**
 * Created by ruslankuziak on 12/14/17.
 */

public class AuthorizationActivity extends AppCompatActivity {

    private static final int RC_GOOGLE_SIGN_IN = 1001;
    private static final int RC_EMAIL_SIGN_IN = 1002;
    private static final int RC_FACEBOOK_SIGN_IN = 1003;

    private View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            MainActivity.start(this);
            return;
        }

        setContentView(R.layout.activity_authorization);
        findViewById(R.id.btn_google_sign_in).setOnClickListener(onClickListener);
        findViewById(R.id.btn_sign_in_with_email).setOnClickListener(onClickListener);
        findViewById(R.id.btn_sign_in_with_facebook).setOnClickListener(onClickListener);
        rootView = findViewById(R.id.constraint_layout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_GOOGLE_SIGN_IN:
                case RC_EMAIL_SIGN_IN:
                case RC_FACEBOOK_SIGN_IN:
                    MainActivity.start(this);
            }
        } else {
            if (response == null) {
                showSnackMessage("Cancelled");
                return;
            }

            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackMessage("No Internet connection");
                return;
            }

            if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                showSnackMessage("Unknown Error");
                return;
            }
        }
    }

    private void signInWithGoogle() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_GOOGLE_SIGN_IN);
    }

    private void signInWithEmail() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), RC_EMAIL_SIGN_IN);
    }

    private void signInWithFacebook() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                        .build(),
                RC_FACEBOOK_SIGN_IN);
    }

    private void showSnackMessage(@NonNull String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_google_sign_in:
                    signInWithGoogle();
                    break;

                case R.id.btn_sign_in_with_email:
                    signInWithEmail();
                    break;

                case R.id.btn_sign_in_with_facebook:
                    signInWithFacebook();
                    break;
            }
        }
    };
}