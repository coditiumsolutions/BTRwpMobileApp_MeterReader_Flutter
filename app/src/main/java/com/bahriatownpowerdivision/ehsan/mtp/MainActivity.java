package com.bahriatownpowerdivision.ehsan.mtp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    public Button btngo;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btngo = findViewById(R.id.continuebutton);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //new LoadViewTask().execute();
        TextView text = (TextView) findViewById(R.id.appname);
        text.setText(Html.fromHtml("MeterReadingApp<sub><small><small><small><small><font color=\"#0000ff\">Beta</small></small></small></small></sub>"));

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission();
            }
        });
    }
    private void permission()
    {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//                           Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                         gotosecondactivity();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
//       '' return false;
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
        onBackPressed();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
        return;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent i = new Intent(getApplicationContext(), aboutapp.class);
                startActivity(i);
                return true;
            case R.id.action_developer:
                Intent ij = new Intent(getApplicationContext(), developer.class);
                startActivity(ij);
                return true;

            case R.id.readingsave:
                Intent ijj = new Intent(getApplicationContext(), noreading.class);
                startActivity(ijj);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void gotosecondactivity()
    {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_open_translate, R.anim.activty_close_scale);
    }


    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}
