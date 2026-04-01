package com.bahriatownpowerdivision.ehsan.mtp;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by EHSAN on 12/19/2015.
 */
public class help extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

     //   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
       //         + phoneNumber)));


        // Send Text Message

        TextView tv2 = (TextView) findViewById(R.id.textnumber);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:03135103076"));
                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sIntent);
            }
        });

        // Call Dialing
        TextView tv = (TextView) findViewById(R.id.phonenumber);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:03135103076"));
                sIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sIntent);
            }
        });
    }
}
