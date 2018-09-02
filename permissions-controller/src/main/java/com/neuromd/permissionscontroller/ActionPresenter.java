package com.neuromd.permissionscontroller;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.neuromd.javaobserver.INotificationCallback;
import com.neuromd.javaobserver.SubscribersNotifier;

final class ActionPresenter {
    private final PermissionsActivity mActivity;
    private final ImageView mStateImageView;
    private final TextView mStatusTextView;
    private final Button mPerformActionButton;
    private final PermissionAction mAction;
    public final SubscribersNotifier granted = new SubscribersNotifier();

    public ActionPresenter(PermissionsActivity activity, View actionView, PermissionAction action){
        mActivity = activity;
        mStateImageView = actionView.findViewById(R.id.statusImageView);
        mStatusTextView = actionView.findViewById(R.id.actionTextView);
        mPerformActionButton = actionView.findViewById(R.id.performActionButton);
        mAction = action;

        onActionStatusChanged();

        mPerformActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAction.performAction(mActivity);
            }
        });
        mAction.statusChanged.subscribe(new INotificationCallback() {
            @Override
            public void onNotify(Object sender, Object nullParam) {
                onActionStatusChanged();
            }
        });
    }

    public boolean isActionGranted(){
        return mAction.isGranted();
    }

    public void requestPermission(){
        mAction.performAction(mActivity);
    }

    private void onActionStatusChanged(){
        mStateImageView.setImageResource(mAction.isGranted() ? android.R.drawable.ic_input_add : android.R.drawable.ic_delete);
        mStatusTextView.setText(mAction.stateText());
        mPerformActionButton.setVisibility(!mAction.isGranted() ? View.VISIBLE : View.INVISIBLE);
        if (mAction.isGranted()){
            granted.sendNotification(this, null);
        }
    }
}
