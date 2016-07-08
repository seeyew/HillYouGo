package com.seeyewmo.hillyougo.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.seeyewmo.hillyougo.R;
import com.seeyewmo.hillyougo.model.Result;
import com.seeyewmo.hillyougo.service.DataHelper;
import com.seeyewmo.hillyougo.ui.utils.ImageUtil;

/**
 * A fragment representing a single NYTArticle detail screen.
 */
public class NYTArticleDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_SECTION  = "item_section";

    private Result mItem;

    private int mItemId;
    private String mSection;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NYTArticleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mItemId = savedInstanceState.getInt(ARG_ITEM_SECTION);
            mSection = savedInstanceState.getString(ARG_ITEM_SECTION);
        } else if (getArguments().containsKey(ARG_ITEM_ID) && getArguments().containsKey(ARG_ITEM_SECTION)) {
            mItemId = getArguments().getInt(ARG_ITEM_ID);
            mSection = getArguments().getString(ARG_ITEM_SECTION);
            /*Activity activity = this.getActivity();

            if (appBarLayout != null && mItem != null) {
                appBarLayout.setTitle(getArguments().getString(ARG_ITEM_SECTION));
            }*/
        }

        /*CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);

        if (appBarLayout != null && mItem != null) {
            appBarLayout.setTitle(mSection);
        }*/

        //TODO: This is a sync call, consider moving to another thread in the future
        mItem = DataHelper.getInstance(getContext()).getOneArticle(
                mSection, mItemId);


        if (mItem != null) {
            Log.i("DataHelper", "Found it");
        } else {
            //need to handle this
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nytarticle_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.nytarticle_detail)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.nyarticle_date)).setText(mItem.getPublishedDate());
            final String urlToPhoto = ImageUtil.getBestPhotoUrlForArticle(mItem,getDisplayMetrics().widthPixels);
            if (urlToPhoto != null && !urlToPhoto.isEmpty()) {
                Uri imageUri = Uri.parse(urlToPhoto);
                SimpleDraweeView draweeView = (SimpleDraweeView) rootView.findViewById(R.id.sdvImage);
                draweeView.setImageURI(imageUri);
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(NYTArticleDetailFragment.ARG_ITEM_ID, mItemId);
        outState.putString(NYTArticleDetailFragment.ARG_ITEM_SECTION, mSection);
    }

    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        /*int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;*/
        return displaymetrics;
    }

}
