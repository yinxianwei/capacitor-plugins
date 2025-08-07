package com.yinxianwei.ylocation;

import android.app.Activity;
import android.location.Location;
import android.os.Looper;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;

@CapacitorPlugin(name = "Ylocation")
public class YlocationPlugin extends Plugin {

    private Ylocation implementation = new Ylocation();

    @PluginMethod
    public void getCurrentPosition(PluginCall call) {
        Activity activity = getActivity();
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        LocationRequest mLocationRequest = new LocationRequest();
        Boolean enableHighAccuracy = call.getBoolean("enableHighAccuracy",  false);
        if (enableHighAccuracy) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        Integer timeout = call.getInt("timeout", 10000);
        mLocationRequest.setMaxWaitTime(timeout);
        mLocationRequest.setNumUpdates(1);
        LocationCallback  mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                activity.runOnUiThread(() -> {
                    if (locationResult != null) {
                        fusedLocationProviderClient.flushLocations();
                        Location location = locationResult.getLastLocation();

                        JSObject data = new JSObject();
                        data.put("timestamp", location.getTime());

                        JSObject coords = new JSObject();
                        coords.put("latitude", location.getLatitude());
                        coords.put("longitude", location.getLongitude());
                        coords.put("accuracy", location.getAccuracy());
                        coords.put("speed", location.getSpeed());
                        coords.put("altitude", location.getAltitude());
                        data.put("coords", coords);

                        call.resolve(data);
                    } else {
                        call.reject("Unable to retrieve location data");
                    }
                });
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {}
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        call.reject(e.getMessage());
                    }
                });
    }
}
