package io.qxtno.carols.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Carol implements Parcelable {
    public String title;
    public String carol_text;

    public Carol() {
    }

    public Carol(String title, String carol_text) {
        this.title = title;
        this.carol_text = carol_text;
    }

    protected Carol(Parcel in) {
        title = in.readString();
        carol_text = in.readString();
    }

    public static final Creator<Carol> CREATOR = new Creator<Carol>() {
        @Override
        public Carol createFromParcel(Parcel in) {
            return new Carol(in);
        }

        @Override
        public Carol[] newArray(int size) {
            return new Carol[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getCarol_text() {
        return carol_text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(carol_text);
    }
}
