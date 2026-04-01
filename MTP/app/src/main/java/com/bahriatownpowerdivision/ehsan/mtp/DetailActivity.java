package com.bahriatownpowerdivision.ehsan.mtp;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button exit = (Button) findViewById(R.id.exiticon);
        exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void showZaire( View view)
    {
        String button_test;
        button_test =((Button) view).getText().toString();
        if (button_test.equals("By Refr"))
        {
            Intent intent1= new Intent(this,byrefrence.class);
            startActivity(intent1 );
        }

        else if (button_test.equals("By Meter"))
        {
            Intent intent22 = new Intent(this,byMeter.class);
            startActivity(intent22);
        }
        else if (button_test.equals("By Sr"))
        {
            Intent intent45 = new Intent(this,bySerial.class);
            startActivity(intent45);
        }

        else if (button_test.equals("Reading"))
        {
            Intent intent2 = new Intent(this,bybatch.class);
            startActivity(intent2);
        }
        else if(button_test.equals("Export"))
        {
            Intent intent3 = new Intent(this,Exportdata.class);
            startActivity(intent3);
        }
        else if(button_test.equals("Import"))
        {
            Intent intent5 = new Intent(this,importdata.class);
            startActivity(intent5);
        }
        else if(button_test.equals("Verify #1"))
        {
            Intent intent4 = new Intent(this,checkreport.class);
            startActivity(intent4);
        }

        else if(button_test.equals("Verify #2"))
        {
            Intent intent4 = new Intent(this,checkreport2.class);
            startActivity(intent4);
        }

        else if(button_test.equals("Setting"))
        {
            Intent intent9 = new Intent(this,configuration.class);
            startActivity(intent9);
        }

        else if(button_test.equals("nc"))
        {
            Intent intent6 = new Intent(this,newc.class);
            startActivity(intent6);
        }

        else if(button_test.equals("Counter"))
        {
            Intent intent6 = new Intent(this,counter.class);
            startActivity(intent6);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
            case R.id.action_about:
                Intent i = new Intent(getApplicationContext(), aboutapp.class);
                startActivity(i);
                return true;
            case R.id.action_developer:
                Intent ij = new Intent(getApplicationContext(), developer.class);
                startActivity(ij);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }
}
