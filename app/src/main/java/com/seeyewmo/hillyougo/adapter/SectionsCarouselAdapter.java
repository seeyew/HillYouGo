package com.seeyewmo.hillyougo.adapter;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seeyewmo.hillyougo.service.NYTService;
import com.seeyewmo.hillyougo.ui.SectionFragment;

/**
 * Created by seeyew on 7/6/16.
 */
public class SectionsCarouselAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public SectionsCarouselAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public Fragment getItem(int position) {
        //TODO: Total hack, fix this
        final Fragment result;
        switch (position) {
            case 0:
                result = new SectionFragment();
                break;
            case 1:
                result = new SectionFragment();
                break;
            case 2:
                result = new SectionFragment();
                break;
            default:
                result = new SectionFragment();
                break;
        }
        if (result != null) {
            final Bundle bundle = new Bundle();
            bundle.putInt(SectionFragment.FRAGMENT_NUM_KEY, position);
            result.setArguments(bundle);
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        if (position >= NYTService.ALL_SECTIONS.length) {
            return null;
        }

        return NYTService.ALL_SECTIONS[position];
        /*

        switch (position) {
            case 0:
                return NYTService.ALL_SECTIONS[0];
            case 1:
                return "News2";
            case 2:
                return "News3";
            case 3:
                return "News4";
            case 4:
                return "News5";
            default:
                return null;
        }*/
    }

    @Override
    public int getCount() {
        return 5;
    }
}
