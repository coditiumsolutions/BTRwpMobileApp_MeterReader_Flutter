/*
 * Copyright (c) 2016. This App is Created by Ehsan Ul Haq & Used for Bahria Town official Used . Any other personal or Business can cause strict legal Action .
 */
package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by EXHAN on 1/31/2016.
 */
public class configuration extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView tv=(TextView)findViewById(R.id.wifimac);
        TextView tv1=(TextView)findViewById(R.id.owner);
        TextView tv2=(TextView)findViewById(R.id.shortname);
        String androidID = Settings.System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        TextView tv3=(TextView)findViewById(R.id.currentdatetime);
        tv.setText("Android Device ID :" + androidID);
        String owner = "";
        String shortname="";

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm a");
        String strDate = sdf.format(c.getTime());
        if (androidID.equals("60ec98d17e2eb222")) { owner = "Mustfeez Ur Rehman";shortname = "MR";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("dc71ec99b985746e")) { owner = "Kashif Hanif";shortname = "KH";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("a9aff77ca635a181")) { owner = "Mansoor Liaqat";shortname = "ML";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("11955c8e9759f409")) { owner = "Muhammad Musab Sultan";shortname = "MM";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("205cdc7cd0caab7e")) { owner = "Etsham Khaliq";shortname = "EK"; tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("6cfc812c76b3a38e")) { owner = "AHSAN UL HAQ";shortname = "EXH"; tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("855ddc19f2b72710")) { owner = "Muhammad Mubashar";shortname = "MM"; tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("b70dd092049c278c")) { owner = "Muhammad Yousaf";shortname = "MY"; tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate);  }
        else if (androidID.equals("675ca3fbac75e8a3")) { owner = "Mian Imran Akhtar";shortname = "MIA"; tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("474a99348d463219")) { owner = "Mudassir Ameen";shortname = "MA"; tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }

//          Phase 8 Meter Reader
        else if (androidID.equals("af669d13929dd2ac")) { owner = "Seemab Ishtiaq";shortname = "SI";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("d95ba7ec583f1afe")) { owner = "Sajjad Hussain";shortname = "SH";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("4bf439a3813a8e8b")){ owner = "Hassan Fida";shortname = "HF";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("b0bfbbba199a6eef")) { owner = "Tayyab Mehmood";shortname = "TM";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("c673b880dbc5dabf")) { owner = "Faiz Ul Hassan";shortname = "FH";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("bbb0b371e12aa47e")) { owner = "Ahad Mehmood";shortname = "AM";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("3c6c2808f362729b")) { owner = "Moiz Rajpoot";shortname = "MR";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("ea00e8ff18ebca0e")) { owner = "Saad Hafeez";shortname = "SM";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("970440d4f9ce7927")) { owner = "Rashid Abbas";shortname = "RA";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("d8f320b88a1c3604")) { owner = "Zeeshan Ahmed";shortname = "ZA";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("7a5a28a153c6fc0e")) { owner = "Muhammad Mohsin";shortname = "MK";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("aa2ef86e759dacfb")) { owner = "Shahab Ahmed";shortname = "SA";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("cb2dab9dac54cf6f")) { owner = "Manan Qadeer";shortname = "MQ";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }
        else if (androidID.equals("e3d7e11a2ce0f55d")) { owner = "Mubashar Nawaz";shortname = "MN";tv1.setText("Owner Name : " + owner);tv2.setText("Owner Short Name : " + shortname);tv3.setText("" + strDate); }

//            BAHRIA ENCLAVE BILLING TEAM
        else if (androidID.equals("96E70AD0BD45769B")) { owner = "Taimoor Hussain";shortname = "TM"; }
        else if (androidID.equals("DFC48E5E15B96475")) { owner = "Zulfiqar Ahmed";shortname = "ZF"; }
        else if (androidID.equals("E9108BBEAD020BC7")) { owner = "Mohsin Shareef";shortname = "MS"; }
        else if (androidID.equals("3CCB4AF0B639EC46")) { owner = "Faizan Haider";shortname = "FH"; }
        else { owner = "";shortname = ""; }



    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
