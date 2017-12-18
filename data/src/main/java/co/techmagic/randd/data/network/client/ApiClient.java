package co.techmagic.randd.data.network.client;

import java.util.concurrent.TimeUnit;

import co.techmagic.aand.data.BuildConfig;
import co.techmagic.aand.data.network.service.NewsService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public class ApiClient {

    private static ApiClient client;

    private ApiClient() {
        OkHttpClient client = buildClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(NewsService.class);
    }

    public static synchronized ApiClient getInstance() {
        if (client == null) {
            client = new ApiClient();
        }

        return client;
    }

    private OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        return builder.build();
    }
}