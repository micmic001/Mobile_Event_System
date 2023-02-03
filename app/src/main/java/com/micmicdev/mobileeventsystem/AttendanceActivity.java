package com.micmicdev.mobileeventsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.micmicdev.mobileeventsystem.MOD.AlertsModule;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AttendanceActivity extends AppCompatActivity {
    private DecoratedBarcodeView barcodeViewAttendance;
    private ViewfinderView viewfinderViewAttendance;
    private String lastText;
    private BeepManager beepManager;
    ImageView g, s, t, g2, s2;
    TextView resultText, resultText2, scannerStat;
    ImageButton backToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_attendance);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeObjects();
        buttonTriggers();
        beepManager = new BeepManager(this);
        barcodeViewAttendance.decodeContinuous(callbackAttendance);
        barcodeViewAttendance.resume();
    }

    public void initializeObjects(){
        backToMenu = findViewById(R.id.bt_backToMenuAttendance);
        scannerStat = findViewById(R.id.scannerStat);
        g = findViewById(R.id.stat_green);
        s = findViewById(R.id.stat_red);
        barcodeViewAttendance = findViewById(R.id.barcode_view);
    }

    //--Zxing barcode decoder.(Attendance)
    private BarcodeCallback callbackAttendance = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {

            lastText = result.getText();
            //getLocalHost.setText(lastText);
            beepManager.playBeepSoundAndVibrate();
            AlertsModule alert = new AlertsModule(AttendanceActivity.this);
            alert.generalMessage(lastText);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 300);
            barcodeViewAttendance.pause();
            barcodeViewAttendance.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    private void buttonTriggers(){
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentBackToMenu();
            }
        });
    }

    private void intentBackToMenu(){
        Intent i = new Intent(AttendanceActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onBackPressed(){
        intentBackToMenu();
        finish();
    }
}