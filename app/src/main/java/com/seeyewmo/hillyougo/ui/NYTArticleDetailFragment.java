package com.seeyewmo.hillyougo.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.seeyewmo.hillyougo.R;
import com.seeyewmo.hillyougo.model.Result;
import com.seeyewmo.hillyougo.service.DataService;
import com.seeyewmo.hillyougo.ui.utils.ImageUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a single NYTArticle detail screen.
 */
public class NYTArticleDetailFragment extends Fragment {
    private static final String TAG = "NYTDetailFragment";
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_SECTION  = "item_section";

    private Result mArticle;
    private int mItemId;
    private String mSection;

    @Bind(R.id.article_date)
    TextView mArticleDate;

    @Bind(R.id.article_title)
    TextView mArticleTitle;

    @Bind(R.id.sdvImage)
    SimpleDraweeView mDraweeView;

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

        }

        //TODO: This is a sync call, consider moving to another thread in the future
        mArticle = DataService.getInstance(getContext()).getOneArticle(
                mSection, mItemId);

        if (mArticle != null) {
            //TODO: What now?
            Log.e(TAG, "Can't load the article");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nytarticle_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        if (mArticle != null) {
            mArticleTitle.setText(mArticle.getTitle());
            mArticleDate.setText(mArticle.getPublishedDate());
            final String urlToPhoto = ImageUtil.getBestPhotoUrlForArticle(mArticle);
            if (urlToPhoto != null && !urlToPhoto.isEmpty()) {
                Uri imageUri = Uri.parse(urlToPhoto);
                mDraweeView.setImageURI(imageUri);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(NYTArticleDetailFragment.ARG_ITEM_ID, mItemId);
        outState.putString(NYTArticleDetailFragment.ARG_ITEM_SECTION, mSection);
    }

    /*
    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        return displaymetrics;
    }*/

}
