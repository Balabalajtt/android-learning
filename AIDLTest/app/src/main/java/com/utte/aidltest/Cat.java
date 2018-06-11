package com.utte.aidltest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 江婷婷 on 2018/6/7.
 */

public class Cat implements Parcelable {

    public int catColor;
    public String catName;

    public Cat(int catColor, String catName) {
        this.catColor = catColor;
        this.catName = catName;
    }

    private Cat(Parcel in) {
        catColor = in.readInt();
        catName = in.readString();
    }

    public static final Creator<Cat> CREATOR = new Creator<Cat>() {
        @Override
        public Cat createFromParcel(Parcel in) {
            return new Cat(in);
        }

        @Override
        public Cat[] newArray(int size) {
            return new Cat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(catColor);
        dest.writeString(catName);
    }

}
