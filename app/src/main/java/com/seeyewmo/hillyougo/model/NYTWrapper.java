package com.seeyewmo.hillyougo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by seeyew on 7/7/16.
 */
public class NYTWrapper {
    //TODO: Switch this one hour
    private static final long STALE_MS = 30 * 1000; // Data is stale after 30 seconds

    @JsonSerialize(as = NYTResponse.class)
    private NYTResponse nytResponse;

    @JsonProperty("timestamp")
    private long timestamp;

    public NYTWrapper() {
        this.timestamp = System.currentTimeMillis();
    }

    public NYTWrapper(NYTResponse value) {
        this.nytResponse = value;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isUpToDate() {
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }

    @JsonProperty("timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(long timestamp) {
       this.timestamp = timestamp;
    }

    public List<Result> getResults() {
        return this.nytResponse.getResults();
    }

    @JsonSerialize(as = NYTResponse.class)
    public NYTResponse getNYTResponse() {
        return this.nytResponse;
    }

    @JsonSerialize(as = NYTResponse.class)
    public void setNYTResponse(NYTResponse nytResponse) {
        this.nytResponse = nytResponse;
    }
}
