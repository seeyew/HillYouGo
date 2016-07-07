package com.seeyewmo.hillyougo.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @param baseUrl REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz, final String baseUrl) {
        //Gson gson = new GsonBuilder().registerTypeAdapter(clazz, new MyDeserialier()).create();
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        */
        final ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        T service = retrofit.create(clazz);

        return service;
    }

    /*
    String apiKey = "YOUR-API-KEY-HERE";

    RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addQueryParam("apikey", apiKey);
        }
    };
    // Add the interceptor to OkHttpClient
    OkHttpClient client = new OkHttpClient();
    client.interceptors().add(requestInterceptor);

    Retrofit retrofit = new Retrofit.Builder()
               .client(client)
               .baseUrl("http://api.rottentomatoes.com/api/public/v1.0")
               .build();

    */
}
