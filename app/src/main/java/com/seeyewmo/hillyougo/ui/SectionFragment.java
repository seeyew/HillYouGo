package com.seeyewmo.hillyougo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.seeyewmo.hillyougo.R;
import com.seeyewmo.hillyougo.adapter.NYTCardAdapter;
import com.seeyewmo.hillyougo.model.NYTWrapper;
import com.seeyewmo.hillyougo.model.Result;
import com.seeyewmo.hillyougo.service.DataHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by seeyew on 7/7/16.
 */
public class SectionFragment extends android.support.v4.app.Fragment {
    public static final String FRAGMENT_SECTION_PATH = "section_path";
    private String mSection;
    private DataHelper mDataHelper;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    private NYTCardAdapter mCardAdapter;

    public static SectionFragment newInstance(String path) {
        SectionFragment sectionFragment = new SectionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SectionFragment.FRAGMENT_SECTION_PATH, path);
        sectionFragment.setArguments(bundle);
        return sectionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_section, null);
    }


    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, getView());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mCardAdapter = new NYTCardAdapter(new NYTCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Result item) {
                Intent intent = new Intent(getContext(), NYTArticleDetailActivity.class);
                intent.putExtra(NYTArticleDetailFragment.ARG_ITEM_ID, position);
                intent.putExtra(NYTArticleDetailFragment.ARG_ITEM_SECTION, mSection);
                getContext().startActivity(intent);
            }
        });


        mRecyclerView.setAdapter(mCardAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_refresh:
                loadData(true);
                return true;
            case R.id.action_clear:
                mCardAdapter.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("DataHelper", "!!!!Saving section!!!! " + mSection);
        outState.putString(FRAGMENT_SECTION_PATH, mSection);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mSection = savedInstanceState.getString(FRAGMENT_SECTION_PATH);
        } else if (getArguments() != null) {
            Bundle args = getArguments();
            mSection = args.getString(FRAGMENT_SECTION_PATH);
        }
        mDataHelper = DataHelper.getInstance(getActivity());
        loadData(false);
    }

    private void loadData(boolean isRefresh) {

        mDataHelper.getArticles(mSection, isRefresh).subscribe(new Subscriber<NYTWrapper>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NYTWrapper nytWrapper) {
                if (getActivity() != null) {
                    if (nytWrapper != null && nytWrapper.getResults() != null) {
                        mCardAdapter.addAllData(nytWrapper.getResults());
                    }
                }
            }
        });
    }
}
