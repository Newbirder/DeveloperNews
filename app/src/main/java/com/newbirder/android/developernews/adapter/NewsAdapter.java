package com.newbirder.android.developernews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.newbirder.android.developernews.R;
import com.newbirder.android.developernews.entities.Image;

import java.util.List;

/**
 * Created by Newbirder on 15/11/30.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private List<Image> imageList;

    public NewsAdapter(Context context, List<Image> imageList) {
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.imageList = imageList;

    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        NewsViewHolder newsViewHolder = new NewsViewHolder(layoutInflater.inflate(
                            R.layout.item_image, parent, false));
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Glide.with(mContext)
                .load(imageList.get(position).getUrl())
//                .load(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
//        Log.e("gg", String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public NewsViewHolder(View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.picture);
        }
    }
}
