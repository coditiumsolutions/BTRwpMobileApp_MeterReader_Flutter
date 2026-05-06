package com.bahriatownpowerdivision.ehsan.mtp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
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
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.CALL_PHONE);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
            permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
        } else {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // Allow app launch when core permissions are granted.
                        if (hasCorePermissions()) {
                            gotosecondactivity();
                            return;
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                            openAllFilesAccessSettings();
                            return;
                        }

                        // Show settings only when denied state blocks core flow.
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Please allow Camera and Storage/Photos permissions.",
                                    Toast.LENGTH_SHORT
                            ).show();
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

    private boolean hasCorePermissions() {
        boolean cameraGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;

        if (!cameraGranted) {
            return false;
        }

        boolean allFilesGranted = Build.VERSION.SDK_INT < Build.VERSION_CODES.R || Environment.isExternalStorageManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            boolean mediaGranted = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED;
            return mediaGranted || allFilesGranted;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            boolean readGranted = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED;
            return readGranted || allFilesGranted;
        }

        boolean readGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
        boolean writeGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
        return readGranted && writeGranted;
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

    private void openAllFilesAccessSettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Intent fallbackIntent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivity(fallbackIntent);
        }
    }

}
