package com.seeyewmo.hillyougo.ui;

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
import com.seeyewmo.hillyougo.service.NYTService;
import com.seeyewmo.hillyougo.service.ServiceFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by seeyew on 7/7/16.
 */
public class SectionFragment extends android.support.v4.app.Fragment{
    public static final String FRAGMENT_NUM_KEY = "fragment_num_key";

    @Bind(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.button_clear)
    protected Button mButtonClear;

    @Bind(R.id.button_fetch)
    protected Button  mButtonFetch;

    final NYTCardAdapter mCardAdapter = new NYTCardAdapter();

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
        mRecyclerView.setAdapter(mCardAdapter);

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardAdapter.clear();
            }
        });

        mButtonFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NYTService service = ServiceFactory.createRetrofitService(NYTService.class, NYTService.SERVICE_ENDPOINT);
                service.getArticles("politics", 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<NYTWrapper>() {
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
                                List<Result> results = nytWrapper.getResults();
                                for (Result result : results) {
                                    mCardAdapter.addData(result);
                                }
                            }
                        });
            }

        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
