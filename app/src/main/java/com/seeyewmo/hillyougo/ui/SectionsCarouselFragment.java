package com.seeyewmo.hillyougo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seeyewmo.hillyougo.R;
import com.seeyewmo.hillyougo.adapter.SectionsCarouselAdapter;
import com.seeyewmo.hillyougo.ui.widget.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by seeyew on 7/6/16.
 */
public class SectionsCarouselFragment extends android.support.v4.app.Fragment{
    @Bind(R.id.tpi_header)
    //protected TitlePageIndicator indicator;
    //protected TabPageIndicator indicator;
    protected SlidingTabLayout indicator;

    @Bind(R.id.vp_pages)
    protected ViewPager pager;

    private SectionsCarouselAdapter mSectionCarouselAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSectionCarouselAdapter = new SectionsCarouselAdapter(getResources(), getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_section_carousel, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getView());

        pager.setAdapter(mSectionCarouselAdapter);
        indicator.setDistributeEvenly(true);
        indicator.setViewPager(pager);
        pager.setCurrentItem(0);
    }

    public void setCurrentItem(int position) {
        pager.setCurrentItem(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mSectionCarouselAdapter.getItem(pager.getCurrentItem()).onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
