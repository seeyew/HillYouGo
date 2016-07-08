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
        "type",
        "subtype",
        "caption",
        "copyright",
        "media-metadata"
})
public class Medium {

    @JsonProperty("type")
    private String type;
    @JsonProperty("subtype")
    private String subtype;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("copyright")
    private String copyright;
    @JsonProperty("media-metadata")
    private List<MediaMetadatum> mediaMetadata = new ArrayList<MediaMetadatum>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * The subtype
     */
    @JsonProperty("subtype")
    public String getSubtype() {
        return subtype;
    }

    /**
     *
     * @param subtype
     * The subtype
     */
    @JsonProperty("subtype")
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    /**
     *
     * @return
     * The caption
     */
    @JsonProperty("caption")
    public String getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     * The caption
     */
    @JsonProperty("caption")
    public void setCaption(String caption) {
        this.caption = caption;
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
     * The mediaMetadata
     */
    @JsonProperty("media-metadata")
    public List<MediaMetadatum> getMediaMetadata() {
        return mediaMetadata;
    }

    /**
     *
     * @param mediaMetadata
     * The media-metadata
     */
    @JsonProperty("media-metadata")
    public void setMediaMetadata(List<MediaMetadatum> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public MediaMetadatum getClosestMediaMetadatumPhoto() {
        Long biggest = Long.MIN_VALUE;
        MediaMetadatum largestMedia = null;
        for (MediaMetadatum mediaMetadatum : getMediaMetadata()) {
            Long newWidth = mediaMetadatum.getWidth();
            Long newHeight = mediaMetadatum.getHeight();
            Long newSize = newWidth * newHeight;
            if (newSize > biggest) {
                biggest = newSize;
                largestMedia = mediaMetadatum;
            }
        }
        return largestMedia;
    }
}
