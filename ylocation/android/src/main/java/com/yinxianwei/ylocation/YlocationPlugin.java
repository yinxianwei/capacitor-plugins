package com.yinxianwei.ylocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;

@CapacitorPlugin(name = "Ylocation",
        permissions = {
                @Permission(
                        alias = "Ylocation",
                        strings = {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        }
                )
        }
)
public class YlocationPlugin extends Plugin {

    private Ylocation implementation = new Ylocation();
    private Handler handler;
    private Runnable timeoutRunnable;

    @Override
    public void load() {
        super.load();
        handler = new Handler(Looper.getMainLooper());
    }

    @PluginMethod
    public void requestPermissions(PluginCall call) {
        if (getPermissionState("Ylocation") != PermissionState.GRANTED) {
            requestPermissionForAlias("Ylocation", call, "locationPermsCallback");
        } else {
            call.resolve();
        }
    }
    @PermissionCallback
    private void locationPermsCallback(PluginCall call) {
        if (getPermissionState("Ylocation") == PermissionState.GRANTED) {
            call.resolve();
        } else {
            call.reject("Location permission denied");
        }
    }
    private void locationSuccess(PluginCall call, Location location) {
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
    }
    @PluginMethod
    public void getCurrentPosition(PluginCall call) {
        getActivity().runOnUiThread(() -> {
            startLocateOnce(call);
        });
    }
    private void startLocateOnce(PluginCall call) {
        Integer timeout = call.getInt("timeout", 5000);
        Boolean enableHighAccuracy = call.getBoolean("enableHighAccuracy", false);
        LocationRequest mLocationRequest = new LocationRequest();
        if (enableHighAccuracy) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        mLocationRequest.setNumUpdates(1);
        LocationCallback  mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                clearLocationTimeout();
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    locationSuccess(call, location);
                } else {
                    call.reject("Unable to retrieve location data");
                }
            }
        };
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        clearLocationTimeout();
                        call.reject(e.getMessage());
                    }
                });
        timeoutRunnable = () -> {
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        locationSuccess(call, location);
                    } else {
                        call.reject("Location request timed out");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    call.reject("Location request timed out");
                }
            });
        };
        handler.postDelayed(timeoutRunnable, timeout);
    }
    private void clearLocationTimeout() {
        if (timeoutRunnable != null) {
            handler.removeCallbacks(timeoutRunnable);
            timeoutRunnable = null;
        }
    }
}
