package com.resumesamples.resumesamples.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.resumesamples.resumesamples.BuildConfig;
import com.resumesamples.resumesamples.MyApp;
import com.resumesamples.resumesamples.R;
import com.resumesamples.resumesamples.Storage;
import com.resumesamples.resumesamples.adapter.RecyclerAdapter;
import com.resumesamples.resumesamples.model.Title;
import com.resumesamples.resumesamples.parser.XmlPullParserHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements RecyclerAdapter.OnClickListener {
    public static final String TITLES_XML = "titles.xml";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private InterstitialAd mInterstitialAd;
    private Title titleCurrent = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void attachActivityViews(Bundle savedInstanceState) {
        super.attachActivityViews(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.list);
    }

    @Override
    protected void initActivityViews(Bundle savedInstanceState) {
        super.initActivityViews(savedInstanceState);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(buildQuestionList(), this);
        recyclerView.setAdapter(adapter);

        if (Storage.getInstance().isFirstAppStart()) {
            Storage.getInstance().setFirstAppStart();
            showInformationDialog(R.string.alert_use_cookies);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initInterstitialAds();
    }

    private void initInterstitialAds() {
        String adUnitId = MyApp.isDebugBuild() ? "ca-app-pub-3940256099942544/1033173712" : getResources().getString(R.string.banner_interstitial_id);
        if (!adUnitId.trim().isEmpty()) {

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(adUnitId);

            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {

                    onClick(titleCurrent);
                }
            });
        }
    }

    private ArrayList<Title> buildQuestionList() {
        ArrayList<Title> titles = new ArrayList<>();
        try {
            XmlPullParserHandler parser = new XmlPullParserHandler();
            InputStream is = getAssets().open(TITLES_XML);
            titles = parser.parse(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        titles.add(new Title(getResources().getString(R.string.privacy_policy), getResources().getString(R.string.privacy_policy_url)));
        titles.add(new Title(getResources().getString(R.string.contact_developer), getResources().getString(R.string.contact_developer_url)));
        titles.add(new Title(getResources().getString(R.string.more_apps), getResources().getString(R.string.more_apps_url)));
        return titles;
    }

    protected boolean isHomeAsUpEnabledShown() {
        return false;
    }

    @Override
    public void onClick(Title title) {
        titleCurrent = title;
        if (title.isInterstitialShown() && mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            startActivity(WebActivity.getIntent(this, title));
        }
    }
}