package com.neuromd.permissionscontroller;

public final class GeolocationAdapterAction implements PermissionAction {
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

    }

    @Override
    public void performAction() {

    }
}
