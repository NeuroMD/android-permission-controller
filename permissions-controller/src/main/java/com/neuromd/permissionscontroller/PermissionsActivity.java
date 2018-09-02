package com.neuromd.permissionscontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neuromd.javaobserver.INotificationCallback;
import com.neuromd.javaobserver.SubscribersNotifier;

import java.util.List;

public final class PermissionsActivity extends Activity {

    class PermissionsResultArgs {
        final int RequestCode;
        final String[] Permissions;
        final int[] GrantResults;

        PermissionsResultArgs(int requestCode, String[] permissions, int[] grantResults){
            RequestCode = requestCode;
            Permissions = permissions;
            GrantResults = grantResults;
        }
    }

    class ActivityResultArgs {
        final int RequestCode;
        final int ResultCode;
        final Intent Data;

        ActivityResultArgs(int requestCode, int resultCode, Intent data){
            RequestCode = requestCode;
            ResultCode = resultCode;
            Data = data;
        }
    }

    private final ActionPresenterList mPresenters = new ActionPresenterList();
    private Button mSkipButton;
    private TextView mWarningTextView;
    public SubscribersNotifier<PermissionsResultArgs> permissionsResultReceived = new SubscribersNotifier<>();
    public SubscribersNotifier<ActivityResultArgs> activityResultReceived = new SubscribersNotifier<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions_activity);

        mSkipButton = findViewById(R.id.skipButton);
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWarningTextView = findViewById(R.id.warningTextView);

        String actionString = getIntent().getStringExtra("ActionString");
        List<PermissionAction> actionList = ActionFactory.parseActionString(actionString);
        createActionControls(actionList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        permissionsResultReceived.sendNotification(this, new PermissionsResultArgs(requestCode, permissions, grantResults));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        activityResultReceived.sendNotification(this, new ActivityResultArgs(requestCode, resultCode, data));
    }

    private void createActionControls(List<PermissionAction> actionList){
        mPresenters.allGranted.subscribe(new INotificationCallback() {
            @Override
            public void onNotify(Object sender, Object nParam) {
                onAllPermissionsGranted();
            }
        });
        for (PermissionAction action : actionList) {
            LinearLayout actionsLayout = findViewById(R.id.actionsLayout);
            View actionLayout = LayoutInflater.from(this).inflate(R.layout.action_layout, null);
            ActionPresenter presenter = new ActionPresenter(this, actionLayout, action);
            mPresenters.add(presenter);
            actionsLayout.addView(actionLayout);
        }
        if (mPresenters.isAllGranted()){
            onAllPermissionsGranted();
        }
        else{
            mPresenters.requestPermissions();
        }
    }

    private void onAllPermissionsGranted(){
        mWarningTextView.setVisibility(View.INVISIBLE);
        mSkipButton.setVisibility(View.INVISIBLE);
        finish();
    }
}
