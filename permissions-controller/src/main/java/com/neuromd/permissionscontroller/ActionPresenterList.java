package com.neuromd.permissionscontroller;

import com.neuromd.javaobserver.INotificationCallback;
import com.neuromd.javaobserver.SubscribersNotifier;

import java.util.ArrayList;
import java.util.List;

final class ActionPresenterList {
    private final List<ActionPresenter> mPresenters = new ArrayList<>();
    public SubscribersNotifier allGranted = new SubscribersNotifier();

    public void add(ActionPresenter presenter){
        mPresenters.add(presenter);
        presenter.granted.subscribe(new INotificationCallback() {
            @Override
            public void onNotify(Object sender, Object nullParam) {
                if (isAllGranted()){
                    allGranted.sendNotification(this, null);
                }
            }
        });
    }

    public boolean isAllGranted(){
        for (ActionPresenter presenter : mPresenters){
            if (!presenter.isActionGranted()){
                return false;
            }
        }
        return true;
    }

    public void requestPermissions(){
        for (ActionPresenter presenter : mPresenters){
            presenter.requestPermission();
        }
    }
}
