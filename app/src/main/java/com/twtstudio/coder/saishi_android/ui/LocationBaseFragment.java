package com.twtstudio.coder.saishi_android.ui;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by clifton on 16-4-6.
 */
public class LocationBaseFragment extends Fragment implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
