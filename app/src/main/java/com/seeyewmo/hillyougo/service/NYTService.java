package com.seeyewmo.hillyougo.service;

import com.seeyewmo.hillyougo.model.NYTWrapper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by seeyew on 7/6/16.
 */
public interface NYTService {
    final String API_KEY_PARAM = "api-key";
    final String KEY = "8672bf1fd49d4a608fe6dec6f080ef79";
    final String SERVICE_ENDPOINT = "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/";

    final String[] ALL_SECTIONS = new String[]{ "politics", "business", "arts", "sports", "health"};

    @GET("{section}/{time-period}.json")
    Observable<NYTWrapper> getArticles(@Path("section") String section, @Path("time-period") int period);
}
