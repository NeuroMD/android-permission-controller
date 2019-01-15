package com.neuromd.permissionscontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.neuromd.javaobserver.INotificationCallback;

public final class GeolocationAdapterAction extends PermissionAction {
    private static ActionFactory.ActionConstructor actionConstructor(){
        return new ActionFactory.ActionConstructor() {
            @Override
            public PermissionAction construct() {
                return new GeolocationAdapterAction();
            }
        };
    }

    public static String actionString(){
        return GeolocationAdapterAction.class.getName();
    }

    static {
        ActionFactory.registerAction(actionString(), actionConstructor());
    }

    private GeolocationAdapterAction(){
        mIsGranted = false;
        mStatusText = "Location service disabled";
    }

    private boolean mIsGranted;
    private String mStatusText;
    private INotificationCallback<PermissionsActivity.ActivityResultArgs> onActivityResult = new INotificationCallback<PermissionsActivity.ActivityResultArgs>() {
        @Override
        public void onNotify(final Object activity, PermissionsActivity.ActivityResultArgs args) {
            if (args.RequestCode != 113){
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ((PermissionsActivity)activity).activityResultReceived.unsubscribe(onActivityResult);
                }
            }).start();
            mIsGranted = isGpsEnabled((PermissionsActivity)activity);
            if (mIsGranted){
                onGranted();
            }
        }
    };

    @Override
    public void performAction(PermissionsActivity activity) {
        if (mIsGranted) {
            return;
        }
        if (!isGpsEnabled(activity)){
            activity.activityResultReceived.subscribe(onActivityResult);
            Intent enableGeoIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivityForResult(enableGeoIntent, 113);
        }
        else{
            mIsGranted = true;
            onGranted();
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

    private boolean isGpsEnabled(Activity activity){
        final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE );
        if (manager == null){
            return false;
        }
        return manager.isProviderEnabled( LocationManager.GPS_PROVIDER );
    }

    private void onGranted() {
        mStatusText = "Location service enabled";
        statusChanged.sendNotification(this, null);
    }
}
