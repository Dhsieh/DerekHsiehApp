package Utils.RetroFit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by derekhsieh on 4/3/16.
 */
public class RetroFitInterface {
    //Joey's Home Address
    //private static String baseUrl = "http://192.168.1.14:4567";

    //B&B Wifi
    //private static String baseUrl = "http://192.168.1.225:4567";
    //
     private static String baseUrl = "http://192.168.0.162:4567";

    public static Retrofit createRetroFit(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        }).build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(client).build();
    }

    public static ToPost createToPost(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        }).build();

       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(client).build();

        return retrofit.create(ToPost.class);
    }


    public static ToGet createToGet(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(client).build();

        return retrofit.create(ToGet.class);
    }
}

