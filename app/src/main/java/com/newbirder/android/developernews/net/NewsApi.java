package com.newbirder.android.developernews.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.newbirder.android.developernews.entities.Image;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Newbirder on 15/11/30.
 */
public class NewsApi {


    public static NewsApi instance;

    public static NewsApi getInstance() {
        if (instance == null) {
            synchronized (NewsApi.class) {
                if (instance == null) {
                    instance = new NewsApi();
                }
            }
        }
        return instance;
    }

    public static final int FETCH_COUNT = 10;


    private final OkHttpClient client = new OkHttpClient();

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();

    private final NewsService newsService;

    public NewsApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gank.avosapps.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        newsService = retrofit.create(NewsService.class);

    }

    public interface NewsService {
        @GET("data/%E7%A6%8F%E5%88%A9/{count}/1")
        Call<Result<List<Image>>> latest(@Path("count") int count);

        class Result<T> {

            public boolean error;

            public T results;

        }
    }

    public Call<NewsService.Result<List<Image>>> getLatest() {
        return newsService.latest(FETCH_COUNT);
    }
}
