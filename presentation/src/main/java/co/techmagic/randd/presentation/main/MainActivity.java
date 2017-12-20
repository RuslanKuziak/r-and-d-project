package co.techmagic.randd.presentation.main;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import co.techmagic.randd.R;
import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.presentation.BaseActivity;
import co.techmagic.randd.presentation.auth.AuthorizationActivity;
import co.techmagic.randd.presentation.profile.ProfileActivity;

public class MainActivity extends BaseActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArticlesAdapter adapter;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewModel();
        initUi();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.articles.observe(this, articlesObserver);
    }

    private void initUi() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.articles_progress_bar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ArticlesAdapter();
        recyclerView.setAdapter(adapter);
        findViewById(R.id.iv_profile).setOnClickListener(onClickListener);
        findViewById(R.id.iv_logout).setOnClickListener(onClickListener);
    }

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

    private void signOut() {
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, AuthorizationActivity.class));
                            finish();
                        }
                    }
                });
    }

    private Observer<List<ArticleApp>> articlesObserver = new Observer<List<ArticleApp>>() {
        @Override
        public void onChanged(@Nullable List<ArticleApp> articleApps) {
            progressBar.setVisibility(View.GONE);
            adapter.refresh(articleApps);
        }
    };
}