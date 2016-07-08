package com.seeyewmo.hillyougo.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seeyewmo.hillyougo.service.NYTService;
import com.seeyewmo.hillyougo.ui.SectionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seeyew on 7/6/16.
 */
public class SectionsCarouselAdapter extends FragmentPagerAdapter {

    private final Resources resources;
    private final List<android.support.v4.app.Fragment> mSectionFragments = new ArrayList<>(5);
    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public SectionsCarouselAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
        //initFragmentSections();
    }

    private void initFragmentSections() {
        /*Fragment sectionFragment;
        Bundle bundle;
        int position = 0;
        for (String section : NYTService.ALL_SECTIONS) {
            mSectionFragments.add(SectionFragment.newInstance(NYTService.ALL_SECTIONS[position++]));
        }*/
    }

    @Override
    public Fragment getItem(int position) {
        return SectionFragment.newInstance(NYTService.ALL_SECTIONS[position]);
        /*
        if (position < mSectionFragments.size()) {
            return mSectionFragments.get(position);
        }
        return null;*/
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        if (position >= NYTService.ALL_SECTIONS.length) {
            return null;
        }

        return NYTService.ALL_SECTIONS[position];
    }

    @Override
    public int getCount() {
        return 5;//mSectionFragments.size();
    }
}
