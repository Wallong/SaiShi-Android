package com.twtstudio.coder.saishi_android.ui;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.LocationReceiver;

/**
 * Created by clifton on 16-4-6.
 */
public abstract class LocationBaseFragment extends Fragment implements LocationListener {

    private LocationManager locationManager;

    public abstract void onLocationFailed(int failType);

    public abstract LocationConfiguration getLocationConfiguration();

    public abstract void onLocationChanged(Location location);

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void getLocation() {
        if (locationManager != null) {
            locationManager.get();
        }
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        locationManager = new LocationManager(getLocationConfiguration()).on(getActivity()).notify(locationReceiver);
    }

    @Override
    public void onDestroy() {
        locationManager.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        locationManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final LocationReceiver locationReceiver = new LocationReceiver() {
        @Override
        public void onLocationChanged(Location location) {
            LocationBaseFragment.this.onLocationChanged(location);
        }

        @Override
        public void onLocationFailed(int type) {
            LocationBaseFragment.this.onLocationFailed(type);
        }

        @Override
        public void onProviderDisabled(String provider) {
            LocationBaseFragment.this.onProviderDisabled(provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            LocationBaseFragment.this.onProviderEnabled(provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationBaseFragment.this.onStatusChanged(provider, status, extras);
        }
    };
}
