package com.neuromd.permissionscontroller;

public final class BluetoothAdapterAction implements PermissionAction {
    private static ActionFactory.ActionConstructor actionConstructor(){
        return new ActionFactory.ActionConstructor() {
            @Override
            public PermissionAction construct() {
                return new BluetoothAdapterAction();
            }
        };
    }

    public static String actionString(){
        return BluetoothPermissionAction.class.getName();
    }

    static {
        ActionFactory.registerAction(actionString(), actionConstructor());
    }

    private BluetoothAdapterAction(){

    }

    @Override
    public void performAction() {

    }
}
