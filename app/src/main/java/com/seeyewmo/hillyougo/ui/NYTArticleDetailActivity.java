package com.seeyewmo.hillyougo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.seeyewmo.hillyougo.R;

/**
 * An activity representing a single NYTArticle detail screen.
 */
public class NYTArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this); //setup FB Fresco
        setContentView(R.layout.activity_nytarticle_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(NYTArticleDetailFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(NYTArticleDetailFragment.ARG_ITEM_ID,0));
            arguments.putString(NYTArticleDetailFragment.ARG_ITEM_SECTION,
                    getIntent().getStringExtra(NYTArticleDetailFragment.ARG_ITEM_SECTION));
            NYTArticleDetailFragment fragment = new NYTArticleDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nytarticle_detail_container, fragment)
                    .commit();
            toolbar.setTitle( getIntent().getStringExtra(NYTArticleDetailFragment.ARG_ITEM_SECTION));
        }

        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent backToHomeIntent = new Intent(this, MainActivity.class);
            backToHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            navigateUpTo(backToHomeIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
