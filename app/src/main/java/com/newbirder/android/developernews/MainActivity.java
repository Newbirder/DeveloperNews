package com.newbirder.android.developernews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.newbirder.android.developernews.adapter.NewsAdapter;
import com.newbirder.android.developernews.entities.Image;
import com.newbirder.android.developernews.net.NewsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refresher;
    private RecyclerView.LayoutManager layoutManager;
    private NewsAdapter newsAdapter;
    private List<Image> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        refresher = (SwipeRefreshLayout) findViewById(R.id.refresher);
        mRecyclerView = (RecyclerView) findViewById(R.id.content);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        imageList = new ArrayList<>(20);

        newsAdapter = new NewsAdapter(this, imageList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(newsAdapter);

        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchLatestNews();
                refresher.setRefreshing(false);
            }
        });
//        fetchLatestNews();
    }

    private void fetchLatestNews() {
        NewsApi.getInstance().getLatest().enqueue(new LatestCallback());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LatestCallback implements Callback<NewsApi.NewsService.Result<List<Image>>> {
        @Override
        public void onResponse(Response<NewsApi.NewsService.Result<List<Image>>> response) {
            List<Image> list = response.body().results;
//            for(int i = 0; i < imageList.size(); i++) {
//                Log.e("gg", imageList.get(i).getUrl());
//            }
            imageList.addAll(list);
            newsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("throw", t.getMessage());
        }
    }
}
