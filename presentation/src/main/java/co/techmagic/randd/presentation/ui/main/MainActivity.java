package co.techmagic.randd.presentation.ui.main;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import co.techmagic.randd.R;
import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.network.NetworkErrors;
import co.techmagic.randd.presentation.broadcast.ConnectivityBroadcastReceiver;
import co.techmagic.randd.presentation.ui.base.BaseActivity;
import co.techmagic.randd.presentation.ui.base.auth.AuthorizationActivity;
import co.techmagic.randd.presentation.ui.profile.ProfileActivity;

public class MainActivity extends BaseActivity<MainViewModel> {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArticlesAdapter adapter;
    private MainViewModel mainViewModel;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = initViewModel();
        initUi();
    }

    @Override
    protected MainViewModel initViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.articles.observe(this, articlesObserver);
        viewModel.progressLiveData.observe(this, progressObserver);
        viewModel.networkErrorLiveData.observe(this, networkErrorsObserver);
        return viewModel;
    }

    @Override
    public void onNetworkConnectionChanged(ConnectivityBroadcastReceiver.ConnectionStates connectionState) {
        super.onNetworkConnectionChanged(connectionState);
        handleConnectivityStates(connectionState);
    }

    private void handleConnectivityStates(ConnectivityBroadcastReceiver.ConnectionStates connectionState) {
        switch (connectionState) {
            case CONNECTED:
                showSnackMessage(findViewById(R.id.root_view),"Connected to the network", Color.WHITE);
                mainViewModel.getTopHeadlines();
                break;

            case NO_CONNECTION:
                showSnackMessage(findViewById(R.id.root_view),"Connection error", Color.RED);
                break;
        }
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AuthorizationActivity.start(MainActivity.this);
                            finish();
                        }
                    }
                });
    }

    private void initUi() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.app_name));
        progressBar = findViewById(R.id.articles_progress_bar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ArticlesAdapter();
        recyclerView.setAdapter(adapter);
        findViewById(R.id.iv_profile).setOnClickListener(onClickListener);
        findViewById(R.id.iv_logout).setOnClickListener(onClickListener);
    }

    private Observer<List<ArticleApp>> articlesObserver = new Observer<List<ArticleApp>>() {
        @Override
        public void onChanged(@Nullable List<ArticleApp> articleApps) {
            adapter.refresh(articleApps);
        }
    };

    private Observer<Boolean> progressObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean show) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    };

    private Observer<NetworkErrors> networkErrorsObserver = new Observer<NetworkErrors>() {
        @Override
        public void onChanged(@Nullable NetworkErrors networkError) {
            if (networkError != null) {
                switch (networkError) {
                    case UNAUTHORIZED:
                        AuthorizationActivity.start(MainActivity.this);
                        finish();
                        break;

                    case BAD_REQUEST:
                        break;

                    case CONNECTION_ERROR:
                        showSnackMessage(findViewById(R.id.root_view),"Connection error", Color.RED);
                        break;
                }
            }
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_profile:
                    ProfileActivity.start(MainActivity.this);
                    break;

                case R.id.iv_logout:
                    signOut();
                    break;
            }
        }
    };
}