package co.techmagic.randd.presentation.ui.splash;

import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.techmagic.randd.R;
import co.techmagic.randd.presentation.ui.base.BaseActivity;
import co.techmagic.randd.presentation.ui.base.auth.AuthorizationActivity;
import co.techmagic.randd.presentation.ui.main.MainActivity;

public class SplashActivity extends BaseActivity<Void> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handleNavigation();
            }
        }, 2000);
    }

    @Override
    protected Void initViewModel() {
        return null;
    }

    private void handleNavigation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            AuthorizationActivity.start(this);
        } else {
            MainActivity.start(this);
        }
    }
}