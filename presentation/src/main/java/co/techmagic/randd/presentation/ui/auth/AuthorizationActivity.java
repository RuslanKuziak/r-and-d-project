package co.techmagic.randd.presentation.ui.auth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;

import co.techmagic.randd.R;
import co.techmagic.randd.presentation.ui.articles.ListArticlesActivity;
import co.techmagic.randd.presentation.ui.base.BaseActivity;

/**
 * Created by ruslankuziak on 12/14/17.
 */

public class AuthorizationActivity extends BaseActivity<Void> {

    private static final int RC_GOOGLE_SIGN_IN = 1001;
    private static final int RC_EMAIL_SIGN_IN = 1002;
    private static final int RC_FACEBOOK_SIGN_IN = 1003;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, AuthorizationActivity.class));
        activity.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewIntoRoot(R.layout.activity_authorization);
        initUi();
    }

    @Override
    protected Void initViewModel() {
        return null;
    }

    @Override
    protected boolean withToolbar() {
        return false;
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
                    ListArticlesActivity.start(this);
            }
        } else {
            if (response == null) {
                showSnackMessage(findViewById(R.id.constraint_layout), "Cancelled", Color.WHITE);
                return;
            }

            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackMessage(findViewById(R.id.constraint_layout), "No Internet connection", Color.WHITE);
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

    private void initUi() {
        findViewById(R.id.btn_google_sign_in).setOnClickListener(onClickListener);
        findViewById(R.id.btn_sign_in_with_email).setOnClickListener(onClickListener);
        findViewById(R.id.btn_sign_in_with_facebook).setOnClickListener(onClickListener);
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