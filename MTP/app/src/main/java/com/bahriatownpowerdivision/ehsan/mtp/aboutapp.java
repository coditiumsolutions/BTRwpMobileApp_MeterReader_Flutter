package com.bahriatownpowerdivision.ehsan.mtp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by EHSAN on 12/19/2015.
 */
public class aboutapp extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutapp);
        TextView tv1 = (TextView) findViewById(R.id.aboutapp);
        TextView tv = (TextView) findViewById(R.id.chidtext);
        TextView tv2 = (TextView) findViewById(R.id.copyright);
        TextView tv3 = (TextView) findViewById(R.id.copyright2);
        Typeface face = Typeface.createFromAsset(getAssets(), "mono55.ttf");
        tv.setTypeface(face);
        tv1.setTypeface(face);
        tv2.setTypeface(face);
        tv3.setTypeface(face);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        // This activity is start
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
}
