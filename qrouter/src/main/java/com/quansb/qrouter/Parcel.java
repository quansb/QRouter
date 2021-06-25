package com.quansb.qrouter;

import android.os.Bundle;
import java.io.Serializable;

/**
 * @author quanshibao
 * @description
 * @date 2020/11/3
 */
public class Parcel implements IParcel<Bundle> {

    private Bundle mBundle;

    public Parcel(){
        obtain();
    }

    public Parcel putByte(String key, byte value) {
        mBundle.putByte(key, value);
        return this;
    }

    public Parcel putChar(String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }

    public Parcel putShort(String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }

    public Parcel putInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public Parcel putLong(String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public Parcel putFloat(String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public Parcel putDouble(String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public Parcel putBoolean(String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public Parcel putString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public Parcel putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public Parcel putBundle(String key, Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    public Parcel putAll(Bundle bundle) {
        mBundle.putAll(bundle);
        return this;
    }

    @Override
    public Bundle release() {
        if (mBundle != null) {
            mBundle.clear();
            mBundle=null;
        }
        return mBundle;
    }

    @Override
    public Bundle obtain() {
        if (mBundle == null) {
            mBundle = new Bundle();
        } else {
            mBundle.clear();
        }
        return mBundle;
    }
}
