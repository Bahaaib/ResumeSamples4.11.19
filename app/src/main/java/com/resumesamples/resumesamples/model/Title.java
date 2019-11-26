package com.resumesamples.resumesamples.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Title implements Parcelable {
    private String name;
    private String htmlFile;
    private boolean isInterstitialShown;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlFile() {
        return htmlFile;
    }

    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
    }

    public boolean isInterstitialShown() {
        return isInterstitialShown;
    }

    public void setInterstitialShown(boolean interstitialShown) {
        isInterstitialShown = interstitialShown;
    }

    public Title() {
    }

    public Title(String name, String htmlFile) {
        this.name = name;
        this.htmlFile = htmlFile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.htmlFile);
        dest.writeByte(this.isInterstitialShown ? (byte) 1 : (byte) 0);
    }

    protected Title(Parcel in) {
        this.name = in.readString();
        this.htmlFile = in.readString();
        this.isInterstitialShown = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Title> CREATOR = new Parcelable.Creator<Title>() {
        @Override
        public Title createFromParcel(Parcel source) {
            return new Title(source);
        }

        @Override
        public Title[] newArray(int size) {
            return new Title[size];
        }
    };
}
