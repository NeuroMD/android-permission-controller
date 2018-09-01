package com.neuromd.permissionscontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class ActionFactory {
    private ActionFactory(){}

    public interface ActionConstructor{
        PermissionAction construct();
    }

    private static Map<String, ActionConstructor> ActionConstructors = new HashMap<>();

    public static void registerAction(String name, ActionConstructor ctor){
        ActionConstructors.put(name, ctor);
    }

    public static List<PermissionAction> parseActionString(String actionString){
        int semicolonIndex = 0;
        List<PermissionAction> actionList = new ArrayList<>();

        if (actionString == null)
            return actionList;

        while (semicolonIndex < actionString.length()){
            int newSemicolonIndex = actionString.indexOf(";", semicolonIndex);
            if (newSemicolonIndex == -1) {
                newSemicolonIndex = actionString.length();
            }
            String actionName = actionString.substring(semicolonIndex, newSemicolonIndex);
            try {
                actionList.add(createAction(actionName));
            }
            catch (RuntimeException e){
                //skip unknown action string
            }
            semicolonIndex = newSemicolonIndex + 1;
        }
        return actionList;
    }

    private static PermissionAction createAction(String actionName){
        ActionConstructor actionCtor = ActionConstructors.get(actionName);
        if (actionCtor == null){
            throw new RuntimeException("Action with name \"" + actionName + "\" not found");
        }

        PermissionAction action = actionCtor.construct();
        if (action == null){
            throw new RuntimeException("Constructor of action \"" + actionName + "\" failed");
        }
        return action;
    }
}
