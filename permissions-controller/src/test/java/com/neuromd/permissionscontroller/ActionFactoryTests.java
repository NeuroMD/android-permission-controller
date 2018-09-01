package com.neuromd.permissionscontroller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

public class ActionFactoryTests {

    private void parseActionString_emptyList_notnull_noexcept(String actionString){
        List<PermissionAction> actionList = null;
        try {
            actionList = ActionFactory.parseActionString(actionString);
        }
        catch (Exception e){
            fail("Exception been thrown: " + e.getClass().getName() + " with message: " + e.getMessage());
        }
        assertTrue("Action list is null", actionList != null);
        assertEquals("Action list is not empty", 0, actionList.size());
    }

    private PermissionAction parseActionString_oneElementList_elementNotNull_ofType_noexcept(String actionString){
        List<PermissionAction> actionList = null;
        try {
            actionList = ActionFactory.parseActionString(actionString);
        }
        catch (Exception e){
            fail("Exception been thrown: " + e.getClass().getName() + " with message: " + e.getMessage());
        }
        assertTrue("Action list is null", actionList != null);
        assertTrue("Action list is empty", actionList.size() > 0);
        assertTrue("Action list has more than one element", actionList.size() == 1);
        return actionList.get(0);
    }

    @Test
    public void parseActionString_emptyString_emptyList_notnull_noexcept(){
        parseActionString_emptyList_notnull_noexcept("");
    }

    @Test
    public void parseActionString_nullString_emptyList_notnull_noexcept(){
        parseActionString_emptyList_notnull_noexcept(null);
    }

    @Test
    public void parseActionString_oneSemicolonString_emptyList_notnull_noexcept(){
        parseActionString_emptyList_notnull_noexcept(";");
    }

    @Test
    public void parseActionString_manySemicolonString_emptyList_notnull_noexcept(){
        parseActionString_emptyList_notnull_noexcept(";;;;;;;;");
    }

    @Test
    public void parseActionString_garbageString_emptyList_notnull_noexcept(){
        parseActionString_emptyList_notnull_noexcept("JKHS;A&F^#Ujk3;fd8y23jI&(*");
    }

    @Test
    public void parseActionString_BluetoothAdapterActionString_oneElementList(){
        String actionString = BluetoothAdapterAction.actionString();
        PermissionAction action = parseActionString_oneElementList_elementNotNull_ofType_noexcept(actionString);
        assertTrue("Created action is not of type BluetoothAdapterAction", action instanceof BluetoothAdapterAction);
    }

    @Test
    public void parseActionString_BluetoothPermissionActionString_oneElementList(){
        String actionString = BluetoothPermissionAction.actionString();
        PermissionAction action = parseActionString_oneElementList_elementNotNull_ofType_noexcept(actionString);
        assertTrue("Created action is not of type BluetoothPermissionAction", action instanceof BluetoothPermissionAction);
    }

    @Test
    public void parseActionString_GeolocationAdapterActionString_oneElementList(){
        String actionString = GeolocationAdapterAction.actionString();
        PermissionAction action = parseActionString_oneElementList_elementNotNull_ofType_noexcept(actionString);
        assertTrue("Created action is not of type GeolocationAdapterAction", action instanceof GeolocationAdapterAction);
    }

    @Test
    public void parseActionString_GeolocationPermissionActionString_oneElementList(){
        String actionString = GeolocationPermissionAction.actionString();
        PermissionAction action = parseActionString_oneElementList_elementNotNull_ofType_noexcept(actionString);
        assertTrue("Created action is not of type GeolocationPermissionAction", action instanceof GeolocationPermissionAction);
    }
}