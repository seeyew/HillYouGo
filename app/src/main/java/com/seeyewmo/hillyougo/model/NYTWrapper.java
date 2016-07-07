package com.seeyewmo.hillyougo.model;

import java.util.List;

/**
 * Created by seeyew on 7/7/16.
 */
public class NYTWrapper {
    //TODO: Switch this one hour
    private static final long STALE_MS = 30 * 1000; // Data is stale after 30 seconds

    final NYTResponse value;

    final long timestamp;

    public NYTWrapper(NYTResponse value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isUpToDate() {
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }

    public List<Result> getResults() {
        return this.value.getResults();
    }
}
