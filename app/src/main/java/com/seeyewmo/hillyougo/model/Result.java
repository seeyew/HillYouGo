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
        "column",
        "section",
        "byline",
        "title",
        "abstract",
        "published_date",
        "source"
})
public class Result {

    @JsonProperty("url")
    private String url;
    @JsonProperty("column")
    private String column;
    @JsonProperty("section")
    private String section;
    @JsonProperty("byline")
    private String byline;
    @JsonProperty("title")
    private String title;
    @JsonProperty("abstract")
    private String _abstract;
    @JsonProperty("published_date")
    private String publishedDate;
    @JsonProperty("source")
    private String source;
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
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Generated;
//
//@Generated("org.jsonschema2pojo")
//public class Result {
//
//    @SerializedName("url")
//    @Expose
//    private String url;
//    @SerializedName("column")
//    @Expose
//    private String column;
//    @SerializedName("section")
//    @Expose
//    private String section;
//    @SerializedName("byline")
//    @Expose
//    private String byline;
//    @SerializedName("title")
//    @Expose
//    private String title;
//    @SerializedName("abstract")
//    @Expose
//    private String _abstract;
//    @SerializedName("published_date")
//    @Expose
//    private String publishedDate;
//    @SerializedName("source")
//    @Expose
//    private String source;
//    @SerializedName("des_facet")
//    @Expose
//    private List<String> desFacet = new ArrayList<String>();
//    @SerializedName("org_facet")
//    @Expose
//    private List<String> orgFacet = new ArrayList<String>();
//    @SerializedName("per_facet")
//    @Expose
//    private List<String> perFacet = new ArrayList<String>();
//    @SerializedName("geo_facet")
//    @Expose
//    private String geoFacet;
//    @SerializedName("media")
//    @Expose
//    private List<Medium> media = new ArrayList<Medium>();
//
//    /**
//     *
//     * @return
//     * The url
//     */
//    public String getUrl() {
//        return url;
//    }
//
//    /**
//     *
//     * @param url
//     * The url
//     */
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    /**
//     *
//     * @return
//     * The column
//     */
//    public String getColumn() {
//        return column;
//    }
//
//    /**
//     *
//     * @param column
//     * The column
//     */
//    public void setColumn(String column) {
//        this.column = column;
//    }
//
//    /**
//     *
//     * @return
//     * The section
//     */
//    public String getSection() {
//        return section;
//    }
//
//    /**
//     *
//     * @param section
//     * The section
//     */
//    public void setSection(String section) {
//        this.section = section;
//    }
//
//    /**
//     *
//     * @return
//     * The byline
//     */
//    public String getByline() {
//        return byline;
//    }
//
//    /**
//     *
//     * @param byline
//     * The byline
//     */
//    public void setByline(String byline) {
//        this.byline = byline;
//    }
//
//    /**
//     *
//     * @return
//     * The title
//     */
//    public String getTitle() {
//        return title;
//    }
//
//    /**
//     *
//     * @param title
//     * The title
//     */
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    /**
//     *
//     * @return
//     * The _abstract
//     */
//    public String getAbstract() {
//        return _abstract;
//    }
//
//    /**
//     *
//     * @param _abstract
//     * The abstract
//     */
//    public void setAbstract(String _abstract) {
//        this._abstract = _abstract;
//    }
//
//    /**
//     *
//     * @return
//     * The publishedDate
//     */
//    public String getPublishedDate() {
//        return publishedDate;
//    }
//
//    /**
//     *
//     * @param publishedDate
//     * The published_date
//     */
//    public void setPublishedDate(String publishedDate) {
//        this.publishedDate = publishedDate;
//    }
//
//    /**
//     *
//     * @return
//     * The source
//     */
//    public String getSource() {
//        return source;
//    }
//
//    /**
//     *
//     * @param source
//     * The source
//     */
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    /**
//     *
//     * @return
//     * The desFacet
//     */
//    public List<String> getDesFacet() {
//        return desFacet;
//    }
//
//    /**
//     *
//     * @param desFacet
//     * The des_facet
//     */
//    public void setDesFacet(List<String> desFacet) {
//        this.desFacet = desFacet;
//    }
//
//    /**
//     *
//     * @return
//     * The orgFacet
//     */
//    public List<String> getOrgFacet() {
//        return orgFacet;
//    }
//
//    /**
//     *
//     * @param orgFacet
//     * The org_facet
//     */
//    public void setOrgFacet(List<String> orgFacet) {
//        this.orgFacet = orgFacet;
//    }
//
//    /**
//     *
//     * @return
//     * The perFacet
//     */
//    public List<String> getPerFacet() {
//        return perFacet;
//    }
//
//    /**
//     *
//     * @param perFacet
//     * The per_facet
//     */
//    public void setPerFacet(List<String> perFacet) {
//        this.perFacet = perFacet;
//    }
//
//    /**
//     *
//     * @return
//     * The geoFacet
//     */
//    public String getGeoFacet() {
//        return geoFacet;
//    }
//
//    /**
//     *
//     * @param geoFacet
//     * The geo_facet
//     */
//    public void setGeoFacet(String geoFacet) {
//        this.geoFacet = geoFacet;
//    }
//
//    /**
//     *
//     * @return
//     * The media
//     */
//    public List<Medium> getMedia() {
//        return media;
//    }
//
//    /**
//     *
//     * @param media
//     * The media
//     */
//    public void setMedia(List<Medium> media) {
//        this.media = media;
//    }
//
//}
