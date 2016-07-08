package com.seeyewmo.hillyougo.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "url",
        "format",
        "height",
        "width"
})
public class MediaMetadatum {

    @JsonProperty("url")
    private String url;
    @JsonProperty("format")
    private String format;
    @JsonProperty("height")
    private Long height;
    @JsonProperty("width")
    private Long width;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The url
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The format
     */
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    /**
     *
     * @param format
     * The format
     */
    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     *
     * @return
     * The height
     */
    @JsonProperty("height")
    public Long getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    @JsonProperty("height")
    public void setHeight(Long height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The width
     */
    @JsonProperty("width")
    public Long getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    @JsonProperty("width")
    public void setWidth(Long width) {
        this.width = width;
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
