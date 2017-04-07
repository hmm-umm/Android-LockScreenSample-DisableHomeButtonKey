package com.github.dubu.lockscreenusingservice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by mugku on 2017. 4. 7..
 */

public class PermissionActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 123);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 123:
                if (Settings.canDrawOverlays(this)) {
                    LockscreenUtil.getInstance(this).getPermissionCheckSubject()
                            .onNext(true);
                    finish();
                }
                else {
                    //
                }
                break;
        }
    }

}
