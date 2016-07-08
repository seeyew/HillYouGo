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
        "url",
        "adx_keywords",
        "column",
        "section",
        "byline",
        "type",
        "title",
        "abstract",
        "published_date",
        "source",
        "id",
        "asset_id",
        "views",
        "media"
})
public class Result {

    @JsonProperty("url")
    private String url;
    @JsonProperty("adx_keywords")
    private String adxKeywords;
    @JsonProperty("column")
    private String column;
    @JsonProperty("section")
    private String section;
    @JsonProperty("byline")
    private String byline;
    @JsonProperty("type")
    private String type;
    @JsonProperty("title")
    private String title;
    @JsonProperty("abstract")
    private String _abstract;
    @JsonProperty("published_date")
    private String publishedDate;
    @JsonProperty("source")
    private String source;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("asset_id")
    private Long assetId;
    @JsonProperty("views")
    private Long views;
    @JsonProperty("media")
    private List<Medium> media = new ArrayList<Medium>();
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
     * The adxKeywords
     */
    @JsonProperty("adx_keywords")
    public String getAdxKeywords() {
        return adxKeywords;
    }

    /**
     *
     * @param adxKeywords
     * The adx_keywords
     */
    @JsonProperty("adx_keywords")
    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    /**
     *
     * @return
     * The column
     */
    @JsonProperty("column")
    public String getColumn() {
        return column;
    }

    /**
     *
     * @param column
     * The column
     */
    @JsonProperty("column")
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     *
     * @return
     * The section
     */
    @JsonProperty("section")
    public String getSection() {
        return section;
    }

    /**
     *
     * @param section
     * The section
     */
    @JsonProperty("section")
    public void setSection(String section) {
        this.section = section;
    }

    /**
     *
     * @return
     * The byline
     */
    @JsonProperty("byline")
    public String getByline() {
        return byline;
    }

    /**
     *
     * @param byline
     * The byline
     */
    @JsonProperty("byline")
    public void setByline(String byline) {
        this.byline = byline;
    }

    /**
     *
     * @return
     * The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The _abstract
     */
    @JsonProperty("abstract")
    public String getAbstract() {
        return _abstract;
    }

    /**
     *
     * @param _abstract
     * The abstract
     */
    @JsonProperty("abstract")
    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    /**
     *
     * @return
     * The publishedDate
     */
    @JsonProperty("published_date")
    public String getPublishedDate() {
        return publishedDate;
    }

    /**
     *
     * @param publishedDate
     * The published_date
     */
    @JsonProperty("published_date")
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    /**
     *
     * @return
     * The source
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     * The source
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     * The id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The assetId
     */
    @JsonProperty("asset_id")
    public Long getAssetId() {
        return assetId;
    }

    /**
     *
     * @param assetId
     * The asset_id
     */
    @JsonProperty("asset_id")
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /**
     *
     * @return
     * The views
     */
    @JsonProperty("views")
    public Long getViews() {
        return views;
    }

    /**
     *
     * @param views
     * The views
     */
    @JsonProperty("views")
    public void setViews(Long views) {
        this.views = views;
    }

    /**
     *
     * @return
     * The media
     */
    @JsonProperty("media")
    public List<Medium> getMedia() {
        return media;
    }

    /**
     *
     * @param media
     * The media
     */
    @JsonProperty("media")
    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public MediaMetadatum getMediaPhotoUrl() {
        List<Medium> mediumList = getMedia();

        for(Medium medium : mediumList) {
            //pick the first image
            if(medium.getType().equalsIgnoreCase("image")) {
               return medium.getClosestMediaMetadatumPhoto();
            }
        }
        return null;
    }

}
