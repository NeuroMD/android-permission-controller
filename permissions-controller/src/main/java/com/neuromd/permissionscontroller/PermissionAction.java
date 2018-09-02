package com.neuromd.permissionscontroller;

import com.neuromd.javaobserver.SubscribersNotifier;

public abstract class PermissionAction {
    public SubscribersNotifier statusChanged = new SubscribersNotifier();
    public abstract void performAction(PermissionsActivity activity);
    public abstract boolean isGranted();
    public abstract String stateText();
}
