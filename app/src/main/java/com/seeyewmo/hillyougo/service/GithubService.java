package com.seeyewmo.hillyougo.service;

import com.seeyewmo.hillyougo.model.Github;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubService {
    String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/users/{login}")
    Observable<Github> getUser(@Path("login") String login);
}
