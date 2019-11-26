package com.resumesamples.resumesamples.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.resumesamples.resumesamples.BuildConfig;
import com.resumesamples.resumesamples.MyApp;
import com.resumesamples.resumesamples.R;
import com.resumesamples.resumesamples.model.Title;

public class WebActivity extends BaseActivity {
    public static final String KEY_TITLE = "KEY_TITLE";
    private WebView webView;
    protected ProgressBar mProgressBar;

    public static Intent getIntent(Context context, Title item) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(KEY_TITLE, item);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_web;
    }

    @Override
    protected void attachActivityViews(Bundle savedInstanceState) {
        super.attachActivityViews(savedInstanceState);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_WebActivity);
        webView = (WebView) findViewById(R.id.webView_WebActivity);
    }

    @Override
    protected void initActivityViews(Bundle savedInstanceState) {
        super.initActivityViews(savedInstanceState);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        Bundle extras = getExtras(savedInstanceState);
        if (extras != null) {
            Title item = extras.getParcelable(KEY_TITLE);
            if (!item.getHtmlFile().startsWith("http")) {
                String url;
                if (item.getHtmlFile().equals("CompleteGuideToLandingYourDreamJob")) {
                    launchPdfActivity();
                    finish();
                } else {
                    url = "file:///android_asset/web/" + item.getHtmlFile() + ".html";
                    webView.setVisibility(View.VISIBLE);
                    webView.loadUrl(url);
                    mProgressBar.setVisibility(View.GONE);
                }

            } else {
                webView.loadUrl(item.getHtmlFile());
                webView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        if (newProgress == 100) {
                            mProgressBar.setVisibility(View.GONE);
                            webView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }

        initaAds();
    }

    private void launchPdfActivity(){
        Intent intent = new Intent(WebActivity.this, PdfRenderActivity.class);
        startActivity(intent);
    }


    private void initaAds() {
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.app_id));

        final AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(MyApp.isDebugBuild() ? "ca-app-pub-3940256099942544/6300978111" : getResources().getString(R.string.banner_ad_unit_id));

        LinearLayout adsParent = (LinearLayout) findViewById(R.id.layoutAds);
        adsParent.addView(mAdView);

        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                mAdView.setVisibility(View.GONE);
                super.onAdClosed();
            }
        });
    }

}