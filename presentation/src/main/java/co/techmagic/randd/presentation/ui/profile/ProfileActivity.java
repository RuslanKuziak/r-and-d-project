package co.techmagic.randd.presentation.ui.profile;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import co.techmagic.randd.R;
import co.techmagic.randd.data.application.UserApp;
import co.techmagic.randd.presentation.ui.base.BaseActivity;

public class ProfileActivity extends BaseActivity<ProfileViewModel> {

    private ProfileViewModel viewModel;

    EditText etFirstName;
    EditText etLastName;
    TextView tvFavorites;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getLifecycle().addObserver(new ProfileLifecycleObserver());
        viewModel = initViewModel();
        initUi();
        viewModel.getInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected ProfileViewModel initViewModel() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        ProfileModelFactory factory = new ProfileModelFactory(firebaseDatabase);
        ProfileViewModel viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel.class);
        viewModel.userData.observe(this, userObserver);
        return viewModel;
    }

    // TODO avoid using this method due to collapsing toolbar in #activity_profile layout
    @Override
    protected boolean withToolbar() {
        return false;
    }

    private void initUi() {
        ImageView imageView = findViewById(R.id.image_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        findViewById(R.id.btn_cancel).setOnClickListener(onClickListener);
        findViewById(R.id.btn_save).setOnClickListener(onClickListener);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            /*Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(imageView);*/

            toolbar.setTitle(user.getDisplayName());
        }
    }

    private Observer<UserApp> userObserver = new Observer<UserApp>() {
        @Override
        public void onChanged(@Nullable UserApp userApp) {
            if (userApp != null) {
                etFirstName.setText(userApp.getFirstName());
                etLastName.setText(userApp.getLastName());
            }
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_cancel:
                    etFirstName.setText("");
                    etLastName.setText("");
                    showSnackMessage(findViewById(R.id.root_view_profile), "Canceled");
                    break;

                case R.id.btn_save:
                    viewModel.saveInfo(etFirstName.getText().toString().trim(), etLastName.getText().toString().trim());
                    break;
            }
        }
    };
}