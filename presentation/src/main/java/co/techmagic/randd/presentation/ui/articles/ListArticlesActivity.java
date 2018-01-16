package co.techmagic.randd.presentation.ui.articles;

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
import co.techmagic.randd.presentation.ui.auth.AuthorizationActivity;
import co.techmagic.randd.presentation.ui.base.BaseActivity;
import co.techmagic.randd.presentation.ui.profile.ProfileActivity;

public class ListArticlesActivity extends BaseActivity<ArticlesViewModel> implements ArticlesAdapter.OnBookmarkClickListener {

    private ProgressBar progressBar;
    private TextView tvNoArticles;
    private ArticlesAdapter adapter;
    private ArticlesViewModel articlesViewModel;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ListArticlesActivity.class));
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewIntoRoot(R.layout.activity_articles);
        articlesViewModel = initViewModel();
        initUi();
    }

    @Override
    protected ArticlesViewModel initViewModel() {
        ArticlesViewModel viewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);
        viewModel.articles.observe(this, articlesObserver);
        viewModel.progressLiveData.observe(this, progressObserver);
        viewModel.networkErrorLiveData.observe(this, networkErrorsObserver);
        return viewModel;
    }

    @Override
    protected boolean withToolbar() {
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(ConnectivityBroadcastReceiver.ConnectionStates connectionState) {
        super.onNetworkConnectionChanged(connectionState);
        if (connectionState == ConnectivityBroadcastReceiver.ConnectionStates.CONNECTED) {
            articlesViewModel.getEverythingInRange();
        }
    }

    @Override
    public void onBookmarkClicked(@NonNull ArticleApp item, int position) {
        if (item.isBookmarked()) { // TODO write into DB
            item.setBookmarked(false);
            adapter.notifyItemChanged(position);
            showSnackMessage(findViewById(R.id.root_view), "Removed from bookmarks");
        } else {
            item.setBookmarked(true);
            adapter.notifyItemChanged(position);
            showSnackMessage(findViewById(R.id.root_view), "Bookmarked!");
        }
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(ListArticlesActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AuthorizationActivity.start(ListArticlesActivity.this);
                            finish();
                        }
                    }
                });
    }

    private void initUi() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            TextView tvTitle = toolbar.findViewById(R.id.tv_title);
            View ivProfile = toolbar.findViewById(R.id.iv_profile);
            View ivLogout = toolbar.findViewById(R.id.iv_logout);
            tvTitle.setText(getString(R.string.app_name));
            ivProfile.setOnClickListener(onClickListener);
            ivLogout.setOnClickListener(onClickListener);
            ivProfile.setVisibility(View.VISIBLE);
            ivLogout.setVisibility(View.VISIBLE);
        }

        tvNoArticles = findViewById(R.id.tv_no_articles);
        progressBar = findViewById(R.id.articles_progress_bar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ArticlesAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private Observer<List<ArticleApp>> articlesObserver = new Observer<List<ArticleApp>>() {
        @Override
        public void onChanged(@Nullable List<ArticleApp> articleApps) {
            adapter.refresh(articleApps);
            if (articleApps == null || articleApps.isEmpty()) {
                tvNoArticles.setVisibility(View.VISIBLE);
            } else {
                tvNoArticles.setVisibility(View.GONE);
            }
        }
    };

    private Observer<Boolean> progressObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean show) {
            if (show != null) {
                progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        }
    };

    private Observer<NetworkErrors> networkErrorsObserver = new Observer<NetworkErrors>() {
        @Override
        public void onChanged(@Nullable NetworkErrors networkError) {
            if (networkError != null) {
                switch (networkError) {
                    case UNAUTHORIZED:
                        AuthorizationActivity.start(ListArticlesActivity.this);
                        finish();
                        break;

                    case BAD_REQUEST:
                        break;

                    case CONNECTION_ERROR:
                        showSnackMessage(findViewById(R.id.root_view),"Connection error", Color.RED);
                        articlesViewModel.getCachedArticles();
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
                    ProfileActivity.start(ListArticlesActivity.this);
                    break;

                case R.id.iv_logout:
                    signOut();
                    break;
            }
        }
    };
}