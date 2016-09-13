package ch.supsi.dti.ssiot2016.shimmer.rest;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class RestClient {

    private static final String BASE_REST_URL = "http://api.geonames.org/";

    /**
     * Singleton instance
     */
    private static RestClient sInstance;

    /**
     * Get instance, used in singleton pattern
     * @return
     */
    public static IWebService getInstance() {

        if (sInstance == null) {
            sInstance = new RestClient();
        }

        return sInstance.getWebService();
    }

    /**
     * Client used to make HTTP requests
     */
    private OkHttpClient mHttpClient;

    /**
     * Retrofit instance
     */
    private Retrofit mRetrofit;

    /**
     * Web Service specification
     */
    private IWebService mWebService;

    /**
     * Private constructor, to be compliant with Singleton
     */
    private RestClient() {


        mHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
        mRetrofit   = new Retrofit.Builder().baseUrl(BASE_REST_URL).client(mHttpClient).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
        mWebService = mRetrofit.create(IWebService.class);
    }

    /**
     * Gets the web service instance
     * @return a IWebService instance
     */
    private IWebService getWebService() {
        return mWebService;
    }

    /**
     * Interface to query the web-service
     */
    public interface IWebService {

        @GET("weatherIcaoJSON?ICAO=LSZH&username=supsi")
        Call<ResponseBody> getWeatherInfo();
    }
}
