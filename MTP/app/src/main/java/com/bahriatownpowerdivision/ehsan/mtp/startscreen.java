/*
 * Copyright (c) 2016. This App is Created by Ehsan Ul Haq & Used for Bahria Town official Used . Any other personal or Business can cause strict legal Action .
 */

package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by EXHAN on 2/4/2016.
 */
public class startscreen extends Activity {
    private Handler mHandler = new Handler();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(startscreen.this, MainActivity.class);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, 1200); // 1.8 seconds
    }

}