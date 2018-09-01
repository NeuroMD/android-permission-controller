package com.neuromd.permissionscontroller;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

public class PermissionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions_activity);

        String actionString = getIntent().getStringExtra("ActionString");
        List<PermissionAction> actionList = ActionFactory.parseActionString(actionString);
        createActionControls(actionList);
    }

    private void createActionControls(List<PermissionAction> actionList){

    }
}
