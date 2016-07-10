package com.seeyewmo.hillyougo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.seeyewmo.hillyougo.service.DataService;
import com.seeyewmo.hillyougo.service.RetrofitException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by seeyew on 7/7/16.
 */
public class SectionFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "SectionFragment";
    public static final String FRAGMENT_SECTION_PATH = "section_path";
    private String mSection;
    private DataHelper mDataHelper;
    private DataService mDataService;
    private Subscription mDataServiceSubscription;

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
        //mDataHelper = DataHelper.getInstance(getActivity());
        mDataService = DataService.getInstance(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mDataServiceSubscription != null) {
            mDataServiceSubscription.unsubscribe();
        }
    }

    private void loadData(final boolean isRefresh) {
        if (isRefresh) {
            mDataService.requestRefresh(mSection);
            return;
        }

        mDataServiceSubscription = mDataService.getArticles(mSection).subscribe(new Subscriber<NYTWrapper>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error in reading data from DataService " + e);

                RetrofitException error = (RetrofitException) e;
                //LoginErrorResponse response = error.getBodyAs(LoginErrorResponse.class);
                //TODO: How to we tell clients?
                //requestSubject.onNext(null);
                //Also show error text or no data instead of the ListView
                Snackbar.make(mRecyclerView, String.format(getResources().getString(R.string.data_download_error), e.getMessage()), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNext(NYTWrapper nytWrapper) {
                Log.i("SectionFragment", mSection + " !!new data received!!");
                if (getActivity() != null) {
                    if (nytWrapper != null) {
                        if (nytWrapper.getResults() != null) {
                            Log.i("SectionFragment", mSection + " news added");
                            mCardAdapter.addAllData(nytWrapper.getResults());
                        } else {
                            //no news!
                            Log.i("SectionFragment", mSection + " No News");
                            mCardAdapter.clear();
                        }
                        if (isRefresh) {
                            Snackbar.make(mRecyclerView, R.string.data_updated, Snackbar.LENGTH_LONG).show();
                        }
                    } else if (nytWrapper == null) {
                        Log.i("SectionFragment", mSection + " has no local data");
                    } else if (nytWrapper.getResults() == null) {
                        Log.i("SectionFragment", mSection + " has no news");
                    }
                }
            }
        });
/*
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
                        if (isRefresh) {
                            Snackbar.make(mRecyclerView, R.string.data_updated, Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });*/
    }
}
