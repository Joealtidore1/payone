package com.impact.mobiprints.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

public class SystemInformation {
    static String uuid, serial, manufacturer, version, model, platform;

    @SuppressLint("HardwareIds")
    public static String getUuid(Context context) {

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1)
            uuid = Build.SERIAL;
        else
            uuid = Build.getSerial();
        return uuid;
    }

    public static String getSerial() {
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1)
            serial = Build.SERIAL;
        else
            serial = Build.getSerial();
        return serial;
    }

    public static String getManufacturer() {
        manufacturer = Build.MANUFACTURER;
        return manufacturer;
    }

    public static String getVersion() {
        version = String.valueOf(Build.VERSION.SDK_INT);
        return version;
    }

    public static String getModel() {
        model = Build.MODEL;
        return model;
    }

    public static String getPlatform() {
        return platform;
    }
}
