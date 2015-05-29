package de.otto.roboapp.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;

import de.otto.roboapp.R;
import de.otto.roboapp.ui.activities.base.AbstractUpdatableActivity;
import de.otto.roboapp.ui.util.result.PlacementListAdapter;

/**
 * Created by luca on 24.04.15.
 */
public class ResultsActivity extends AbstractUpdatableActivity {

    private PlacementListAdapter placementListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        WebView webView = (WebView) findViewById(R.id.background_webview);
        webView.loadUrl("file:///android_asset/giphy.gif");

        final ListView resultsListView = (ListView) findViewById(R.id.placement_list);

        placementListAdapter = new PlacementListAdapter(this);

        resultsListView.setAdapter(placementListAdapter);

        final Button button = (Button) findViewById(R.id.backToLobbyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(RoboRegistrationActivity.class);
            }
        });

    }

    @Override
    public void updateActivity() {

    }
}