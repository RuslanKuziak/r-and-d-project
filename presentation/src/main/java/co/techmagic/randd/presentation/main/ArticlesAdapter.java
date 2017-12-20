package co.techmagic.randd.presentation.main;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import co.techmagic.randd.R;
import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.application.ArticleInfoApp;

/**
 * Created by ruslankuziak on 12/20/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewsHolder> {

    private List<ArticleApp> articles = new ArrayList<>();

    @Override
    public ArticlesViewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_article, parent, false);
        return new ArticlesViewsHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticlesViewsHolder holder, int position) {
        final ArticleInfoApp item = articles.get(position).getArticleInfoApp();
        holder.tvTitle.setText(item.getTitle());
        holder.tvDesc.setText(item.getDescription());

        Glide.with(holder.imageView.getContext())
                .load(item.getUrlToImage())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void refresh(@Nullable List<ArticleApp> articles) {
        this.articles.clear();
        if (articles != null) {
            this.articles.addAll(articles);
        }

        notifyDataSetChanged();
    }

    static class ArticlesViewsHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTitle;
        TextView tvDesc;

        public ArticlesViewsHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_photo);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
        }
    }

}