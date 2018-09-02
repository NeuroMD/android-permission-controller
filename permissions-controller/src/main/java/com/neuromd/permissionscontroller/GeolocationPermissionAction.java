package com.neuromd.permissionscontroller;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import com.neuromd.javaobserver.INotificationCallback;

public final class GeolocationPermissionAction extends PermissionAction {
    private static ActionFactory.ActionConstructor actionConstructor(){
        return new ActionFactory.ActionConstructor() {
            @Override
            public PermissionAction construct() {
                return new GeolocationPermissionAction();
            }
        };
    }

    public static String actionString(){
        return GeolocationPermissionAction.class.getName();
    }

    static {
        ActionFactory.registerAction(actionString(), actionConstructor());
    }

    private GeolocationPermissionAction(){
        mIsGranted = Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
        mStatusText = mIsGranted ? "Geolocation permissions are granted" : "Geolocation permissions are not granted";
    }

    private boolean mIsGranted;
    private String mStatusText;
    private INotificationCallback<PermissionsActivity.PermissionsResultArgs> onPermissionsResult = new INotificationCallback<PermissionsActivity.PermissionsResultArgs>() {
        @Override
        public void onNotify(Object activity, PermissionsActivity.PermissionsResultArgs args) {
            if (args.RequestCode != 112) {
                return;
            }
            ((PermissionsActivity) activity).permissionsResultReceived.unsubscribe(onPermissionsResult);
            mIsGranted = isPermissionGranted((PermissionsActivity) activity);
            if (mIsGranted) {
                onGranted();
            }
        }
    };

    @Override
    public void performAction(PermissionsActivity activity) {
        if (mIsGranted){
            return;
        }
        if (isPermissionGranted(activity)){
            mIsGranted = true;
            onGranted();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            activity.permissionsResultReceived.subscribe(onPermissionsResult);
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 112);
        }
    }

    @Override
    public boolean isGranted() {
        return mIsGranted;
    }

    @Override
    public String stateText() {
        return mStatusText;
    }

    private void onGranted(){
        mStatusText = "Geolocation permissions are granted";
        statusChanged.sendNotification(this, null);
    }

    private boolean isPermissionGranted(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
}
