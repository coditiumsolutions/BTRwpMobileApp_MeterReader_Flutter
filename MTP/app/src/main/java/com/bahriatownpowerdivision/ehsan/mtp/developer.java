package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * Created by EHSAN on 12/19/2015.
 */
public class developer extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}

