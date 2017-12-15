package co.techmagic.randd.presentation.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.techmagic.randd.R;
import co.techmagic.randd.presentation.auth.AuthorizationActivity;
import co.techmagic.randd.presentation.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

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

    private void handleNavigation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            AuthorizationActivity.start(this);
        } else {
            MainActivity.start(this);
        }
    }
}