package com.seeyewmo.hillyougo.ui.utils;

import com.seeyewmo.hillyougo.model.MediaMetadatum;
import com.seeyewmo.hillyougo.model.Medium;
import com.seeyewmo.hillyougo.model.Result;

import java.util.List;

/**
 * Created by seeyew on 7/8/16.
 */
public class ImageUtil {
    private static final String IMAGE = "image";
    private ImageUtil() {}

    public static String getBestPhotoUrlForArticle(Result article) {
        //String url = null;
        final List<Medium> mediumList = article.getMedia();
        for(Medium medium : mediumList) {
            //TODO: For now always pick the first image
            if(medium.getType().equalsIgnoreCase(IMAGE)) {
                MediaMetadatum mediaMetadatum =  getClosestMediaMetadatumPhoto(medium);
                return mediaMetadatum != null? mediaMetadatum.getUrl() : null;
            }
        }
        return null;
    }

    private static MediaMetadatum getClosestMediaMetadatumPhoto(Medium medium) {
        //TODO: Get photo with the closest width instead of the biggest one
        MediaMetadatum closestMedia = null;
        Long biggest = Long.MIN_VALUE;

        for (MediaMetadatum mediaMetadatum : medium.getMediaMetadata()) {
            Long newWidth = mediaMetadatum.getWidth();
            Long newHeight = mediaMetadatum.getHeight();
            Long newSize = newWidth * newHeight;
            if (newSize > biggest) {
                biggest = newSize;
                closestMedia = mediaMetadatum;
            }
        }
        return closestMedia;
    }
}
