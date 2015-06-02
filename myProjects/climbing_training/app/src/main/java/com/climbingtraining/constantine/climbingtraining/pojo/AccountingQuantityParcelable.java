package com.climbingtraining.constantine.climbingtraining.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KonstantinSysoev on 31.05.15.
 */
public class AccountingQuantityParcelable implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public final static Parcelable.Creator<AccountingQuantityParcelable> CREATOR
            = new Parcelable.Creator<AccountingQuantityParcelable>() {


        @Override
        public AccountingQuantityParcelable createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public AccountingQuantityParcelable[] newArray(int size) {
            return new AccountingQuantityParcelable[0];
        }
    };
}
