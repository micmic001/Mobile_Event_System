package com.micmicdev.mobileeventsystem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.micmicdev.mobileeventsystem.MOD.AlertsModule;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegistrationActivity extends AppCompatActivity {
    private DecoratedBarcodeView barcodeViewName;
    private DecoratedBarcodeView barcodeViewTix;
    private ViewfinderView viewfinderForName;
    private ViewfinderView viewfinderForTix;
    private BeepManager beepManager;
    private String lastText;
    private CompoundBarcodeView initView;
    Boolean tixPass = false;
    Button  scanName, scanTix, registerViewer;
    ImageButton backToMenu, resetFields;
    EditText seatNo, tixNo, firstName, lastName;
    TextView statusMessage, statusDetails, stat, resultText2, checkerResult, scannerTitle;
    ImageView g, s, t, g2, s2;
    Dialog popNameDialog, popTixDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        beepManager = new BeepManager(this);

        initializeObjects();
        buttonTriggers();
    }

    public void initializeObjects(){
        backToMenu = findViewById(R.id.bt_backToMenu);
        resetFields = findViewById(R.id.bt_resetFields);
        scanName = findViewById(R.id.bt_scanName);
        scanTix = findViewById(R.id.bt_scanTicket);
        registerViewer = findViewById(R.id.bt_registerViewer);
        seatNo = findViewById(R.id.et_seatNo);
        tixNo = findViewById(R.id.et_tixNo);
        firstName = findViewById(R.id.et_fName);
        lastName = findViewById(R.id.et_lName);
        statusMessage = findViewById(R.id.tv_statusMessage);
        statusDetails = findViewById(R.id.tv_statusDetails);
    }

    public void buttonTriggers(){
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstName.equals("") || lastName.equals("") || tixNo.equals("")){
                    intentBackToMenu();
                    finish();
                }
                else{
                    new SweetAlertDialog(RegistrationActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Registration in-progress")
                            .setContentText("Are you sure you want to go back to main menu?")
                            .setCancelText("Nope")
                            .setConfirmText("Yep")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    intentBackToMenu();
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });

        resetFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFieldInputs();
            }
        });

        scanName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popNameScanner();
            }
        });

        scanTix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTixScanner();
            }
        });

        registerViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertsModule alert = new AlertsModule(RegistrationActivity.this);
                alert.successAlert();
                statusMessage.setVisibility(View.VISIBLE);
                statusDetails.setVisibility(View.VISIBLE);

            }
        });
    }

    public void intentBackToMenu(){
        Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void resetFieldInputs(){
        firstName.setText("");
        lastName.setText("");
        seatNo.setText("");
        tixNo.setText("");
        statusMessage.setText("-");
        statusDetails.setText("-");
        statusMessage.setVisibility(View.INVISIBLE);
        statusDetails.setVisibility(View.INVISIBLE);
    }

    public void onBackPressed(){
        if(firstName.equals("") || lastName.equals("") || tixNo.equals("")){
            intentBackToMenu();
            finish();
        }
        else{
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Registration in-progress")
                    .setContentText("Are you sure you want to go back to main menu?")
                    .setCancelText("Nope")
                    .setConfirmText("Yep")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            intentBackToMenu();
                            finish();
                        }
                    })
                    .show();
        }
    }

    //--Zxing barcode decoder.(Name)
    private BarcodeCallback callbackName = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {

            lastText = result.getText();
            parseName(lastText);
            //getLocalHost.setText(lastText);
            beepManager.playBeepSoundAndVibrate();
            barcodeViewName.setVisibility(View.GONE);
            popNameDialog.dismiss();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 300);
            barcodeViewName.pause();
            barcodeViewName.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    //--Zxing barcode decoder.(Ticket)
    private BarcodeCallback callbackTix = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {

            lastText = result.getText();
            validateTix(lastText);
            if(tixPass == true){
                parseTicket(lastText);
            }
            //getLocalHost.setText(lastText);
            beepManager.playBeepSoundAndVibrate();
            barcodeViewTix.setVisibility(View.GONE);
            popTixDialog.dismiss();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 300);
            barcodeViewTix.pause();
            barcodeViewTix.resume();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    private void popNameScanner(){
        popNameDialog = new Dialog(RegistrationActivity.this);
        popNameDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popNameDialog.setContentView(R.layout.scanner_checker);
        popNameDialog.setCancelable(true);
        popNameDialog.setCanceledOnTouchOutside(true);
        popNameDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        popNameDialog.show();

        scannerTitle = popNameDialog.findViewById(R.id.tv_title);
        barcodeViewName = popNameDialog.findViewById(R.id.barcode_view);
        g = popNameDialog.findViewById(R.id.stat_green);
        s = popNameDialog.findViewById(R.id.stat_red);
        viewfinderForName = popNameDialog.findViewById(R.id.zxing_viewfinder_view);
        resultText2 = popNameDialog.findViewById(R.id.resultText);
        checkerResult = popNameDialog.findViewById(R.id.tv_resultChecker);
        stat = popNameDialog.findViewById(R.id.scannerStat);

        scannerTitle.setText("Scan Viewer Name");
        barcodeViewName.decodeContinuous(callbackName);
        barcodeViewName.resume();
        popNameDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                barcodeViewName.pause();
            }
        });
    }

    private void popTixScanner(){
        popTixDialog = new Dialog(RegistrationActivity.this);
        popTixDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popTixDialog.setContentView(R.layout.scanner_checker);
        popTixDialog.setCancelable(true);
        popTixDialog.setCanceledOnTouchOutside(true);
        popTixDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        popTixDialog.show();

        scannerTitle = popTixDialog.findViewById(R.id.tv_title);
        barcodeViewTix = popTixDialog.findViewById(R.id.barcode_view);
        g = popTixDialog.findViewById(R.id.stat_green);
        s = popTixDialog.findViewById(R.id.stat_red);
        viewfinderForTix = popTixDialog.findViewById(R.id.zxing_viewfinder_view);
        resultText2 = popTixDialog.findViewById(R.id.resultText);
        checkerResult = popTixDialog.findViewById(R.id.tv_resultChecker);
        stat = popTixDialog.findViewById(R.id.scannerStat);

        scannerTitle.setText("Scan Viewer Ticket");
        barcodeViewTix.decodeContinuous(callbackTix);
        barcodeViewTix.resume();
        popTixDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                barcodeViewTix.pause();
            }
        });
    }

    public void parseName(String inputName){
        String[] codeRaw;
        codeRaw = inputName.split(Pattern.quote("|"));
        firstName.setText(codeRaw[0]);
        lastName.setText(codeRaw[1]);
    }

    public void parseTicket(String inputTicket){
        String[] codeRaw;
        codeRaw = inputTicket.split(Pattern.quote("|"));
        seatNo.setText(codeRaw[2]);
        tixNo.setText(codeRaw[3]);
    }

    public void validateTix(String inputTicket){
        AlertsModule alert = new AlertsModule(RegistrationActivity.this);
        String[] codeRaw;
        String keyValue = "XQW";
        codeRaw = inputTicket.split(Pattern.quote("|"));
        tixPass = true;
        if(codeRaw[0].equals(keyValue)){

        }else{
            tixPass = false;
            alert.errorInvalidTicket();
            popTixDialog.dismiss();
            barcodeViewTix.pause();
        }
    }
}