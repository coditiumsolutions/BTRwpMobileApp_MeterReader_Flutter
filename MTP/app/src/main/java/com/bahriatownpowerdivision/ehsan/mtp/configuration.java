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
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        TextView tv=(TextView)findViewById(R.id.wifimac);
        TextView tv1=(TextView)findViewById(R.id.owner);
        TextView tv2=(TextView)findViewById(R.id.shortname);
        String x=getMacAddr();

        TextView tv3=(TextView)findViewById(R.id.currentdatetime);
        tv.setText("MAC Address :" + x);
        String owner = "";
        String shortname="";

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm a");
        String strDate = sdf.format(c.getTime());
        if (x.equals("08:21:EF:31:1C:43"))
        {
            owner="Hafeez Hassan";shortname="HH";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);

        }
        else if (x.equals("B4:CE:40:2C:62:DA"))
        {
            owner="Mustfeez Ur Rehman";shortname="MR";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);

        }
        else if (x.equals("A6:D6:80:99:2B:76")) {
            owner = "Mateen Ghulfam";
            shortname = "MG";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("34:82:C5:AC:20:01"))
        {
            owner="Kashif Hanif";shortname="KH";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);

        }
        else if (x.equals("34:82:C5:A8:02:C7"))
        {
            owner="Mudassir Ameen";shortname="MA";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("A2:F6:AF:3C:62:21"))
        {
            owner="Mian Imran Akhtar";shortname="MIA";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("s")) {
            owner = "IMRAN Yousaf";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Nme : " + shortname);
            tv3.setText("" + strDate);
        }

        else if (x.equals("B2:04:A9:06:76:1E"))
        {
            owner="Muhammad Mubashar";shortname="MM";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }

        else if (x.equals("34:82:C5:A7:F9:B5"))
        {
            owner="Etsham Khaliq";shortname="EK";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }

        else if (x.equals("3E:E3:F0:16:AE:29"))
        {
            owner="Muhammad Yousaf";shortname="MY";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("54:FC:F0:E9:A5:CC"))
        {
            owner="ADNAN MUNIR";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("54:FC:F0:E5:FE:DA"))
        {
            owner="EHSAN UL HAQ";shortname="EXH";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("71:1C:68:A0:16:50")) {
            owner="Muhammad Haroon";shortname="MH";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("48:C7:96:2A:AA:25")) {
            owner = "Taimoor";
            shortname = "TM";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("b4:86:55:a0:fb:10")) {
            owner = "Zulfiqar";
            shortname = "ZF";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("14:96:E5:4D:5F:78")) {
            owner = "Khawaja Mohsin";
            shortname = "KM";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }

        else if(x.equals("4C:DD:31:6D:48:17") | address.equals("4C:DD:31:6D:48:17")) {
            owner = "MUHAMMAD UMAIR";
            shortname = "MU";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J5
        }
        else if(x.equals("F4:C2:48:AF:F0:89") | address.equals("F4:C2:48:AF:F0:89")) {
            owner = "Mubashar Nawaz";
            shortname = "MN";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J6
        }
        else if(x.equals("EE:24:08:4F:03:5A") | address.equals("EE:24:08:4F:03:5A")) {
            owner = "BILAL KHAN";
            shortname = "BK";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J6
        }

        else if(x.equals("F0:8A:76:E5:60:E4")| address.equals("F0:8A:76:E5:60:E4")) {
            owner = "SEMAB ISHTIAQ";
            shortname = "SI";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J5
        }
        else if(x.equals("3A:A5:EE:34:C8:0F") | address.equals("3A:A5:EE:34:C8:0F") ) {
            owner = "Sajjad Hussain";
            shortname = "SH";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J5
        }

        else if(x.equals("42:D6:AF:A5:93:CA") | address.equals("42:D6:AF:A5:93:CA") ) {
            owner = "Tanzeel Rashid";
            shortname = "TR";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J5
        }
        else if(x.equals("56:BF:A7:6B:FF:DC") | address.equals("56:BF:A7:6B:FF:DC") ) {
            owner = "Rashid Abbas";
            shortname = "RA";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if(x.equals("1A:32:00:DF:FA:CD") | address.equals("1A:32:00:DF:FA:CD")) {
            owner = "Hassan Fida";
            shortname = "HF";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J5
        }
        else if(x.equals("B0:6F:E0:4E:B4:16") | address.equals("B0:6F:E0:4E:B4:16") ) {
            owner = "Tayyab Mehmood";
            shortname = "TM";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J5
        }
        else if(x.equals("12:07:7B:32:00:76") | address.equals("12:07:7B:32:00:76")) {
            owner = "Faiz Ul Hassan";
            shortname = "FH";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
            //J5
        }
        else if(x.equals("D8:55:75:85:CD:B2") | address.equals("D8:55:75:85:CD:B2")) {
            owner = "Zeeshan Ahmed";
            shortname = "ZA";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if(x.equals("2E:99:10:27:B7:08") | address.equals("2E:99:10:27:B7:08")) {
            owner = "Habib Ur Rehman";
            shortname = "MH";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("48:C7:96:2A:AA:25") | address.equals("48:C7:96:2A:AA:25") ) {
            owner = "Taimoor";
            shortname = "TM";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }

        else if (x.equals("b4:86:55:a0:fb:10") | address.equals("b4:86:55:a0:fb:10")) {
            owner = "Zulfiqar";
            shortname = "ZF";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else if (x.equals("14:96:E5:4D:5F:78") | address.equals("14:96:E5:4D:5F:78")) {
            owner = "Khawaja Mohsin";
            shortname = "KM";tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);}
        else if (x.equals("14:96:E5:4D:5F:78")) {
            owner = "Khawaja Mohsin";
            shortname = "KM";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
        else
        {
            owner = "";shortname="";
            tv1.setText("Owner Name : " + owner);
            tv2.setText("Owner Short Name : " + shortname);
            tv3.setText("" + strDate);
        }
    }
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
