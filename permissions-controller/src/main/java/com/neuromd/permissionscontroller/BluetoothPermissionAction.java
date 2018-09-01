package com.neuromd.permissionscontroller;

public final class BluetoothPermissionAction implements PermissionAction {
    private static ActionFactory.ActionConstructor actionConstructor(){
        return new ActionFactory.ActionConstructor() {
            @Override
            public PermissionAction construct() {
                return new BluetoothPermissionAction();
            }
        };
    }

    public static String actionString(){
        return BluetoothPermissionAction.class.getName();
    }

    static {
        ActionFactory.registerAction(actionString(), actionConstructor());
    }

    private BluetoothPermissionAction(){

    }

    @Override
    public void performAction() {

    }
}
