package com.seeyewmo.hillyougo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.seeyewmo.hillyougo.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsCarouselFragment mSectionCarouselFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initScreen();

        /**
         * Set up Android CardView/RecycleView
         */
        /*RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NYTCardAdapter mCardAdapter = new NYTCardAdapter();
        mRecyclerView.setAdapter(mCardAdapter);*/

        /**
         * START: button set up
         */
        /*Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        bClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardAdapter.clear();
            }
        });

        bFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NYTService service = ServiceFactory.createRetrofitService(NYTService.class, NYTService.SERVICE_ENDPOINT);
                service.getArticles("politics", 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<NYTResponse>() {
                            @Override
                            public void onCompleted() {
                                Log.e("NYTDemo", "Done!!");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("NYTDemo", e.getMessage());
                            }

                            @Override
                            public void onNext(NYTResponse nytWrapper) {
                                List<Result> results = nytWrapper.getResults();
                                for (Result result : results) {
                                    mCardAdapter.addData(result);
                                }
                            }
                        });
                }

        });*/


        /**
         * END: button set up
         */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_politics) {
            // Handle the camera action
            mSectionCarouselFragment.setCurrentItem(0);
        } else if (id == R.id.nav_business) {
            mSectionCarouselFragment.setCurrentItem(1);
        } else if (id == R.id.nav_arts) {
            mSectionCarouselFragment.setCurrentItem(2);
        } else if (id == R.id.nav_sports) {
            mSectionCarouselFragment.setCurrentItem(3);
        } else if (id == R.id.nav_health) {
            mSectionCarouselFragment.setCurrentItem(4);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initScreen() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        mSectionCarouselFragment = new SectionsCarouselFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mSectionCarouselFragment)
                .commit();
    }
}
