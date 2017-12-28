package co.techmagic.randd.data.network.client;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import co.techmagic.randd.data.network.service.NewsService;
import co.techmagic.randd.data.network.service.UserService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public class ApiClient {

    private static final String HOST_URl = "https://newsapi.org";
    private static ApiClient apiClient;
    private static Retrofit retrofit;

    private ApiClient() {
        OkHttpClient client = buildClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized void init() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
    }

    public static void release() {
        apiClient = null;
        retrofit = null;
    }

    public static UserService getUserService() {
        return retrofit.create(UserService.class);
    }

    public static NewsService getNewsService() {
        return retrofit.create(NewsService.class);
    }

    private OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        return builder.build();
    }
}