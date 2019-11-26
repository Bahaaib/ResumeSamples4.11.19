package com.resumesamples.resumesamples.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;
import com.resumesamples.resumesamples.R;
import com.resumesamples.resumesamples.model.Title;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    WheelView wheelView;
    ArrayList<Title> titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        wheelView = (WheelView) findViewById(R.id.wheelview);

        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public Drawable getDrawable(int position) {
                return getLocalDrawable(position);
            }

            @Override
            public int getCount() {
                return 5;
            }
        });

        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                switch (position) {
                    case 0:
                        launchMainActivity();
                        break;
                    case 1:
                        launchWebApp(getTitle(0));
                        break;
                    case 2:
                        launchWebApp(getTitle(1));
                        break;
                    case 3:
                        launchWebApp(getTitle(2));
                        break;
                    case 4:
                        launchWebApp(getTitle(3));
                        break;
                }
            }
        });
    }

    void launchMainActivity() {
        Intent intent = new Intent(HomeScreen.this, MainActivity.class);
        startActivity(intent);
    }

    void launchWebApp(Title title) {
        startActivity(WebActivity.getIntent(this, title));
    }

    Title getTitle(int position) {
        titles = new ArrayList<>();
        titles.add(new Title(getResources().getString(R.string.features), getResources().getString(R.string.features_url)));
        titles.add(new Title(getResources().getString(R.string.contact_developer), getResources().getString(R.string.contact_developer_url)));
        titles.add(new Title(getResources().getString(R.string.more_apps), getResources().getString(R.string.more_apps_url)));
        titles.add(new Title(getResources().getString(R.string.privacy_policy), getResources().getString(R.string.privacy_policy_url)));

        return titles.get(position);
    }

    Drawable getLocalDrawable(int position) {
        int[] iconsList = new int[]{
                R.drawable.information,
                R.drawable.feature,
                R.drawable.contact_developer,
                R.drawable.more_apps,
                R.drawable.legal_privacy
        };
        return getResources().getDrawable(iconsList[position]);
    }
}
