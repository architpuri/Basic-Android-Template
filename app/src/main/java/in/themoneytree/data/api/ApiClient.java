package in.themoneytree.data.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //public static final String baseUrl = "http://ec2-13-233-172-166.ap-south-1.compute.amazonaws.com:8080/";

    public static final String baseUrl="http://192.168.43.21:8080/";
    private static MoneyService moneyService = null;

    private ApiClient() {
        // default constructor needs to be private otherwise it will expose the behavior of singleton
    }

    public static MoneyService getInstance() {
        if (moneyService == null) {
            final OkHttpClient okHttpClient = makeOkHttpClient();
            final Retrofit client = makeRetrofit(okHttpClient);
            moneyService = client.create(MoneyService.class);
        }
        return moneyService;
    }

    @NonNull
    private static Retrofit makeRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @NonNull
    private static OkHttpClient makeOkHttpClient() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //add the interceptor for logging the curl commands
        return new OkHttpClient.Builder()
                /*.addInterceptor(new SecurityInterceptor(PrefManager.getInstance()))*/
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new CurlLoggingInterceptor())
                .readTimeout(50, TimeUnit.SECONDS)
                .connectTimeout(50, TimeUnit.SECONDS)
                .cache(null)
                .build();
    }
}
