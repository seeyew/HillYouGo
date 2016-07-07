package com.seeyewmo.hillyougo.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "status",
        "copyright",
        "num_results",
        "results"
})
public class NYTWrapper {

    @JsonProperty("status")
    private String status;
    @JsonProperty("copyright")
    private String copyright;
    @JsonProperty("num_results")
    private Long numResults;
    @JsonProperty("results")
    private List<Result> results = new ArrayList<Result>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The copyright
     */
    @JsonProperty("copyright")
    public String getCopyright() {
        return copyright;
    }

    /**
     *
     * @param copyright
     * The copyright
     */
    @JsonProperty("copyright")
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     *
     * @return
     * The numResults
     */
    @JsonProperty("num_results")
    public Long getNumResults() {
        return numResults;
    }

    /**
     *
     * @param numResults
     * The num_results
     */
    @JsonProperty("num_results")
    public void setNumResults(Long numResults) {
        this.numResults = numResults;
    }

    /**
     *
     * @return
     * The results
     */
    @JsonProperty("results")
    public List<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    @JsonProperty("results")
    public void setResults(List<Result> results) {
        this.results = results;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

//package com.seeyewmo.hillyougo.model;
//
//import javax.annotation.Generated;
//
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
//public class NYTWrapper {
//
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("copyright")
//    @Expose
//    private String copyright;
//    @SerializedName("num_results")
//    @Expose
//    private Integer numResults;
//    @SerializedName("results")
//    @Expose
//    private List<Result> results = new ArrayList<Result>();
//
//    /**
//     *
//     * @return
//     * The status
//     */
//    public String getStatus() {
//        return status;
//    }
//
//    /**
//     *
//     * @param status
//     * The status
//     */
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    /**
//     *
//     * @return
//     * The copyright
//     */
//    public String getCopyright() {
//        return copyright;
//    }
//
//    /**
//     *
//     * @param copyright
//     * The copyright
//     */
//    public void setCopyright(String copyright) {
//        this.copyright = copyright;
//    }
//
//    /**
//     *
//     * @return
//     * The numResults
//     */
//    public Integer getNumResults() {
//        return numResults;
//    }
//
//    /**
//     *
//     * @param numResults
//     * The num_results
//     */
//    public void setNumResults(Integer numResults) {
//        this.numResults = numResults;
//    }
//
//    /**
//     *
//     * @return
//     * The results
//     */
//    public List<Result> getResults() {
//        return results;
//    }
//
//    /**
//     *
//     * @param results
//     * The results
//     */
//    public void setResults(List<Result> results) {
//        this.results = results;
//    }
//
//}
