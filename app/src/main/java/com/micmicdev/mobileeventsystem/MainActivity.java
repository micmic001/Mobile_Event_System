package com.micmicdev.mobileeventsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    Button gotoRegister, gotoAttendance;
    EditText hostName, deviceUserName;
    SharedPreferences sharedpreferences;
    public static final String MYCONFIG = "MyConfig" ;
    public static final String HOSTNAME = "host_name" ;
    public static final String USERNAME = "user_name" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        permissionControl();
        permissionControlCam();
        gotoRegister = findViewById(R.id.bt_register);
        gotoAttendance = findViewById(R.id.bt_attendance);
        hostName = findViewById(R.id.et_hostName);
        deviceUserName = findViewById(R.id.et_devceUser);
        sharedpreferences = getSharedPreferences(MYCONFIG, Context.MODE_PRIVATE);

        hostName.setText(sharedpreferences.getString(HOSTNAME, ""));
        deviceUserName.setText(sharedpreferences.getString(USERNAME, ""));

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentGoToRegistration();
            }
        });
        gotoAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentGoToAttendance();
            }
        });

        hostName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(HOSTNAME, hostName.getText().toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        deviceUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(USERNAME, deviceUserName.getText().toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void intentGoToRegistration(){
        Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(i);
        finish();
    }

    private void intentGoToAttendance(){
        Intent i = new Intent(MainActivity.this, AttendanceActivity.class);
        startActivity(i);
        finish();
    }

    private void permissionControl(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.INTERNET)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        Toast.makeText(MainActivity.this, "Network Allowed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        Toast.makeText(MainActivity.this, "Network Denied", Toast.LENGTH_LONG).show();
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void permissionControlCam(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        Toast.makeText(MainActivity.this, "Camera Allowed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        Toast.makeText(MainActivity.this, "Camera Denied", Toast.LENGTH_LONG).show();
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void onBackPressed(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit Event Management System");
        builder.setMessage("Are you sure you want to quit?")
                .setCancelable(false)
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })

                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();

    }
}