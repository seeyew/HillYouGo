package com.seeyewmo.hillyougo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.seeyewmo.hillyougo.R;
import com.seeyewmo.hillyougo.adapter.NYTCardAdapter;
import com.seeyewmo.hillyougo.model.NYTWrapper;
import com.seeyewmo.hillyougo.model.Result;
import com.seeyewmo.hillyougo.service.DataHelper;
import com.seeyewmo.hillyougo.service.DataService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by seeyew on 7/7/16.
 */
public class SectionFragment extends android.support.v4.app.Fragment {
    public static final String FRAGMENT_SECTION_PATH = "section_path";
    private String mPath;
    private DataService mDataService;
    private DataHelper mDataHelper;

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.button_clear)
    protected Button mButtonClear;

    @Bind(R.id.button_fetch)
    protected Button mButtonFetch;

    private NYTCardAdapter mCardAdapter;

    public static SectionFragment newInstance(String path) {
        SectionFragment sectionFragment = new SectionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SectionFragment.FRAGMENT_SECTION_PATH, path);
        sectionFragment.setArguments(bundle);
        return sectionFragment;
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
            public void onItemClick(Result item) {
                Intent intent = new Intent(getContext(), NYTArticleDetailActivity.class);
                intent.putExtra(NYTArticleDetailFragment.ARG_ITEM_ID, "1");
                getContext().startActivity(intent);
            }
        });


        mRecyclerView.setAdapter(mCardAdapter);

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardAdapter.clear();
            }
        });

        mButtonFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //loadDataWithCache(true);
                getData(true);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("DataHelper", "!!!!Saving section!!!! " + mPath);
        outState.putString(FRAGMENT_SECTION_PATH, mPath);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mPath = savedInstanceState.getString(FRAGMENT_SECTION_PATH);
        } else if (getArguments() != null) {
            Bundle args = getArguments();
            mPath = args.getString(FRAGMENT_SECTION_PATH);
        }
        mButtonClear.setText(mPath);
        Log.i("DataHelper", "====This section is " + mPath);
        //mDataService = new DataService(this.getActivity(), mPath, 7);
        mDataHelper = DataHelper.getInstance(getActivity());
        //loadDataWithCache(false);
        getData(false);
    }

    private void getData(boolean isRefresh) {
        mButtonClear.setText(mPath);
        mDataHelper.getArticles(mPath, isRefresh).subscribe(new Subscriber<NYTWrapper>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NYTWrapper nytWrapper) {
                if (nytWrapper != null && nytWrapper.getResults() != null) {
                    mCardAdapter.addAllData(nytWrapper.getResults());
                }
            }
        });
    }


    private void loadDataWithCache(boolean refresh) {
        //TODO: Total hack, we should only have on observable
        //TODO: Need to unsubscribe
        mDataService.getArticles(refresh).subscribe(new Subscriber<NYTWrapper>() {
            @Override
            public void onCompleted() {
                Log.e("NYTDemo", "Done!!");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("NYTDemo", e.getMessage());
            }

            @Override
            public void onNext(NYTWrapper nytWrapper) {
                if (nytWrapper == null || nytWrapper.getResults() == null) {
                    return;
                }
                List<Result> results = nytWrapper.getResults();
                if (results != null) {
                    mCardAdapter.addAllData(results);
                }
                if (!nytWrapper.isUpToDate()) {
                    Log.i("SectionFragment", "Calling update because data is stale");
                    loadDataWithCache(true);
                }

            }
        });
    }
}
