package co.techmagic.randd.presentation.ui.profile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import co.techmagic.randd.R;
import co.techmagic.randd.presentation.ui.base.BaseActivity;

public class ProfileActivity extends BaseActivity<ProfileViewModel> {

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initUi();
    }

    @Override
    protected ProfileViewModel initViewModel() {
        ProfileViewModel viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        return viewModel;
    }

    // TODO avoid using this method due to collapsing toolbar in #activity_profile layout
    @Override
    protected boolean withToolbar() {
        return false;
    }

    private void initUi() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setTitle("User Profile");
            toolbar.setNavigationOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}