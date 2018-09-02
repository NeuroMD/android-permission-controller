package com.neuromd.permissionscontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import com.neuromd.javaobserver.INotificationCallback;

public final class BluetoothAdapterAction extends PermissionAction {
    private static ActionFactory.ActionConstructor actionConstructor(){
        return new ActionFactory.ActionConstructor() {
            @Override
            public PermissionAction construct() {
                return new BluetoothAdapterAction();
            }
        };
    }

    public static String actionString(){
        return BluetoothAdapterAction.class.getName();
    }

    static {
        ActionFactory.registerAction(actionString(), actionConstructor());
    }

    private BluetoothAdapterAction(){
        mIsGranted = isBluetoothEnabled();
        mStatusText = mIsGranted ? "Bluetooth adapter enabled" : "Bluetooth adapter disabled";
    }

    private boolean mIsGranted;
    private String mStatusText;

    private INotificationCallback<PermissionsActivity.ActivityResultArgs> onActivityResult = new INotificationCallback<PermissionsActivity.ActivityResultArgs>() {
        @Override
        public void onNotify(Object activity, PermissionsActivity.ActivityResultArgs args) {
            if (args.RequestCode != 111){
                return;
            }
            ((PermissionsActivity)activity).activityResultReceived.unsubscribe(onActivityResult);
            mIsGranted = isBluetoothEnabled();
            if (mIsGranted){
                onGranted();
            }
        }
    };

    @Override
    public void performAction(PermissionsActivity activity) {
        if (mIsGranted){
            return;
        }
        activity.activityResultReceived.subscribe(onActivityResult);
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 111);
    }

    @Override
    public boolean isGranted() {
        return mIsGranted;
    }

    @Override
    public String stateText() {
        return mStatusText;
    }

    private boolean isBluetoothEnabled(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled();
    }

    private void onGranted(){
        mStatusText = "Bluetooth adapter enabled";
        statusChanged.sendNotification(this, null);
    }
}
