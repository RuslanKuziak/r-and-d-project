package co.techmagic.randd.presentation.ui.profile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

    @Override
    protected boolean withToolbar() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUi() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        }
    }
}