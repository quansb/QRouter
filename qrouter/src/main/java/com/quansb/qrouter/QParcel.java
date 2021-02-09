package com.quansb.qrouter;

import android.os.Bundle;
import java.io.Serializable;

/**
 * @author quanshibao
 * @description
 * @date 2020/11/3
 */
public class QParcel implements IParcel<Bundle> {

    private Bundle mBundle;

    public QParcel(){
        obtain();
    }

    public QParcel putByte(String key, byte value) {
        mBundle.putByte(key, value);
        return this;
    }

    public QParcel putChar(String key, char value) {
        mBundle.putChar(key, value);
        return this;
    }

    public QParcel putShort(String key, short value) {
        mBundle.putShort(key, value);
        return this;
    }

    public QParcel putInt(String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public QParcel putLong(String key, long value) {
        mBundle.putLong(key, value);
        return this;
    }

    public QParcel putFloat(String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public QParcel putDouble(String key, double value) {
        mBundle.putDouble(key, value);
        return this;
    }

    public QParcel putBoolean(String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public QParcel putString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    public QParcel putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    public QParcel putBundle(String key, Bundle value) {
        mBundle.putBundle(key, value);
        return this;
    }

    public QParcel putAll(Bundle bundle) {
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
