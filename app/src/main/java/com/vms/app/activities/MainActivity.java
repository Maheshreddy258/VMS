package com.vms.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.vms.app.R;
import com.vms.app.base.BaseActivity;
import com.vms.app.base.LocationSingleTon;
import com.vms.app.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void getLocation() {
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                  //  PrefUtils.getInstance().saveLat(location.getLatitude() + "");
                    //PrefUtils.getInstance().saveLogni(location.getLongitude() + "");
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    LocationSingleTon.instance().setLatLng(latLng);
                    setLocation();
                } else {
                  /*  String lat = PrefUtils.getInstance().geLat();
                    String logni = PrefUtils.getInstance().geLogni();
                    if (!ValidationUtil.isNullOrEmpty(lat) && !ValidationUtil.isNullOrEmpty(logni)) {
                        double double_lat = Double.parseDouble(lat);
                        double double_longni = Double.parseDouble(logni);
                        LatLng latLng = new LatLng(double_lat, double_longni);
                        LocationSingleTon.instance().setLatLng(latLng);
                        setLocation();
                    }*/
                }
            }
        });
    }

    private void setLocation() {
        LatLng latLng = LocationSingleTon.instance().getLatLng();
        if (latLng != null) {
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                /*tv_location.setText(
                        addresses.get(0).getLocality());*/
            } else {
                toast("Unable to get location");
                finish();
            }
        }
    }
}