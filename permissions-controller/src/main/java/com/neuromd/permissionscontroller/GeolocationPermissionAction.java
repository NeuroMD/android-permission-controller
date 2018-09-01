package com.neuromd.permissionscontroller;

public final class GeolocationPermissionAction implements PermissionAction {
    private static ActionFactory.ActionConstructor actionConstructor(){
        return new ActionFactory.ActionConstructor() {
            @Override
            public PermissionAction construct() {
                return new GeolocationPermissionAction();
            }
        };
    }

    public static String actionString(){
        return BluetoothPermissionAction.class.getName();
    }

    static {
        ActionFactory.registerAction(actionString(), actionConstructor());
    }

    private GeolocationPermissionAction(){

    }

    @Override
    public void performAction() {

    }
}
