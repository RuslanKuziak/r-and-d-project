package co.techmagic.randd.presentation.ui.articles;

import android.support.annotation.NonNull;
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

/**
 * Created by ruslankuziak on 12/20/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewsHolder> {

    private List<ArticleApp> articles = new ArrayList<>();
    private OnBookmarkClickListener onBookmarkClickListener;

    public ArticlesAdapter(OnBookmarkClickListener onBookmarkClickListener) {
        this.onBookmarkClickListener = onBookmarkClickListener;
    }

    @Override
    public ArticlesViewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_article, parent, false);
        return new ArticlesViewsHolder(view, onBookmarkClickListener);
    }

    @Override
    public void onBindViewHolder(ArticlesViewsHolder holder, int position) {
        final ArticleApp item = articles.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDesc.setText(item.getDescription());
        holder.ivBookmark.setImageResource(item.isBookmarked() ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_border);
        holder.ivBookmark.setTag(item);

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

    static class ArticlesViewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        ImageView ivBookmark;
        TextView tvTitle;
        TextView tvDesc;

        OnBookmarkClickListener onBookmarkClickListener;

        ArticlesViewsHolder(View itemView, OnBookmarkClickListener onBookmarkClickListener) {
            super(itemView);
            this.onBookmarkClickListener = onBookmarkClickListener;
            imageView = itemView.findViewById(R.id.iv_photo);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            ivBookmark = itemView.findViewById(R.id.iv_bookmark);
            ivBookmark.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ArticleApp item = (ArticleApp) view.getTag();
            if (item == null) {
                return;
            }

            onBookmarkClickListener.onBookmarkClicked(item, getAdapterPosition());
        }
    }

    public interface OnBookmarkClickListener {
        void onBookmarkClicked(@NonNull ArticleApp item, int position);
    }
}