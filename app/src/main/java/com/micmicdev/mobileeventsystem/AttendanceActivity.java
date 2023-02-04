package com.micmicdev.mobileeventsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;
import com.micmicdev.mobileeventsystem.ADAPTERS.attendanceDataAdapter;
import com.micmicdev.mobileeventsystem.API.ApiClientInterface;
import com.micmicdev.mobileeventsystem.API.ApiInterface;
import com.micmicdev.mobileeventsystem.MOD.AlertsModule;
import com.micmicdev.mobileeventsystem.MOD.GetDeviceNameModule;
import com.micmicdev.mobileeventsystem.STR.strEventDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceActivity extends AppCompatActivity {
    private DecoratedBarcodeView barcodeViewAttendance;
    private ViewfinderView viewfinderViewAttendance;
    private String lastText;
    private BeepManager beepManager;
    private List<strEventDetails> dataList = new ArrayList<>();
    private attendanceDataAdapter adapter;
    SharedPreferences sharedpreferences;
    strEventDetails post = null;
    Boolean tixPass = false;
    ImageView g, s, t, g2, s2;
    TextView resultText, resultText2, scannerStat, seatNoDisplay, viewerNameDisplay;
    ImageButton backToMenu;
    String host = "http://192.168.100.8:5000", tid="", fullName="", attServerIn="0", attServerOut="0", deviceUser="";
    Button btIn, btOut;
    RecyclerView listAttendanceData;
    EditText searchMode;
    Runnable myRunnable;
    final Handler handler = new Handler();
    public static final String MYCONFIG = "MyConfig" ;
    public static final String HOSTNAME = "host_name" ;
    public static final String USERNAME = "user_name" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_attendance);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedpreferences = getSharedPreferences(MYCONFIG, Context.MODE_PRIVATE);
        host = sharedpreferences.getString(HOSTNAME, "");
        deviceUser = sharedpreferences.getString(USERNAME, "");

        initializeObjects();
        buttonTriggers();
        beepManager = new BeepManager(this);
        barcodeViewAttendance.decodeContinuous(callbackAttendance);
        barcodeViewAttendance.resume();
        seatNoDisplay.setText("-");
        viewerNameDisplay.setText("-");
        btIn.setEnabled(false);
        btOut.setEnabled(false);


        final int delay = 1000; // 1000 milliseconds == 1 second
        myRunnable = new Runnable() {
            public void run() {
                String value = searchMode.getText().toString();
                if(value.equals("")){
                    checkAttendance("");
                }
                else{
                    checkAttendance(value);
                }
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(myRunnable, delay);
    }

    public void initializeObjects(){
        backToMenu = findViewById(R.id.bt_backToMenuAttendance);
        scannerStat = findViewById(R.id.scannerStat);
        g = findViewById(R.id.stat_green);
        s = findViewById(R.id.stat_red);
        barcodeViewAttendance = findViewById(R.id.barcode_view);
        seatNoDisplay = findViewById(R.id.tv_seatNoVal);
        viewerNameDisplay = findViewById(R.id.tv_nameVal);
        btIn = findViewById(R.id.bt_viewerIn);
        btOut = findViewById(R.id.bt_viewerOut);
        listAttendanceData = findViewById(R.id.rv_dataList);
        searchMode = findViewById(R.id.et_search);

    }

    //--Zxing barcode decoder.(Attendance)
    private BarcodeCallback callbackAttendance = new BarcodeCallback()  {

        @Override
        public void barcodeResult(BarcodeResult result) {

            lastText = result.getText();
            validateTix(lastText);
            if(tixPass == true){
                parseTicket(lastText);
            }
            beepManager.playBeepSoundAndVibrate();
            //AlertsModule alert = new AlertsModule(AttendanceActivity.this);
            //alert.generalMessage(lastText);
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
        AlertsModule alert = new AlertsModule(AttendanceActivity.this);
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentBackToMenu();
            }
        });

        btIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkSeat = seatNoDisplay.getText().toString();
                String checkName = viewerNameDisplay.getText().toString();

                if (checkName.equals("-") && checkSeat.equals("-")){
                    alert.noScannedTicket("Please scan a ticket before assigning a status");
                }
                else {
                    setAttendance("I");
                }
            }
        });

        btOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkSeat = seatNoDisplay.getText().toString();
                String checkName = viewerNameDisplay.getText().toString();

                if (checkName.equals("-") && checkSeat.equals("-")){
                    alert.noScannedTicket("Please scan a ticket before assigning a status");
                }
                else {
                    setAttendance("O");
                }
            }
        });
    }

    private void intentBackToMenu(){
        handler.removeCallbacks(myRunnable);
        Intent i = new Intent(AttendanceActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onBackPressed(){
        intentBackToMenu();
        finish();
    }

    public void parseTicket(String inputTicket){
        String[] codeRaw;
        codeRaw = inputTicket.split(Pattern.quote("|"));
        queryServerViewerData(codeRaw[2], codeRaw[3]);
        //seatNo.setText(codeRaw[2]);
        //tixNo.setText(codeRaw[3]);
        //strSeatNo = seatNo.getText().toString();
    }

    public void validateTix(String inputTicket){
        AlertsModule alert = new AlertsModule(AttendanceActivity.this);
        String[] codeRaw;
        String keyValue = "XQW";
        codeRaw = inputTicket.split(Pattern.quote("|"));
        tixPass = true;
        if(codeRaw[0].equals(keyValue)){

        }else{
            tixPass = false;
            alert.errorInvalidTicket();
        }
    }

    public void queryServerViewerData(String seatNo, String tixNo){
        AlertsModule alert = new AlertsModule(AttendanceActivity.this);
        seatNoDisplay.setText("-");
        viewerNameDisplay.setText("-");
        attServerIn = "0";
        attServerOut = "0";
        try {
            post = new strEventDetails(seatNo, tixNo, "");
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strEventDetails> call = clientInterface.getTicketData(post);

            call.enqueue(new Callback<strEventDetails>() {

                @Override
                public void onResponse(Call<strEventDetails> call, Response<strEventDetails> response) {
                    String serverMessage ="";
                    String seatNo = "";
                    try {
                        serverMessage = response.body().getMessage();
                        fullName = response.body().getFullName();
                        seatNo = response.body().getSeat_no();
                        tid = response.body().getTid();
                        attServerIn = response.body().getInStatServer();
                        attServerOut = response.body().getOutStatServer();
                        seatNoDisplay.setText(seatNo);
                        viewerNameDisplay.setText(fullName);
                        enableAttendanceButtons(attServerIn,attServerOut);
                    }catch (Exception e){
                        alert.variableErrorMessage("Viewer Data Not Found");
                    }
                }

                @Override
                public void onFailure(Call<strEventDetails> call, Throwable t) {
                    alert.variableErrorMessage(t.getMessage());
                }
            });
        }
        catch (Exception e){
            btIn.setEnabled(false);
            btOut.setEnabled(false);
            alert.variableSuccessMessage("Viewer Data Not Found");
            alert.variableErrorMessage(e.getMessage());
        }

    }

    public void setAttendance(String io){
        AlertsModule alert = new AlertsModule(AttendanceActivity.this);
        String deviceName = GetDeviceNameModule.getDeviceName();
        try {
            post = new strEventDetails(tid, fullName, io, deviceName, deviceUser);
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<strEventDetails> call = clientInterface.setViewerAttendance(post);

            call.enqueue(new Callback<strEventDetails>() {
                @Override
                public void onResponse(Call<strEventDetails> call, Response<strEventDetails> response) {
                    String serverMessage ="";
                    try {
                        serverMessage = response.body().getMessage();
                        alert.variableSuccessMessage(serverMessage);
                        if(io.equals("I")){
                            btIn.setEnabled(false);
                            btOut.setEnabled(true);
                        }else if(io.equals("O")){
                            btIn.setEnabled(true);
                            btOut.setEnabled(false);
                        }
                    }catch (Exception e){
                        alert.variableErrorMessage(e.getMessage());
                        btIn.setEnabled(false);
                        btOut.setEnabled(false);
                    }
                }

                @Override
                public void onFailure(Call<strEventDetails> call, Throwable t) {
                    alert.variableErrorMessage(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alert.variableErrorMessage(e.getMessage());
        }
    }

    public void enableAttendanceButtons(String inStatus, String outStatus){
        btIn.setEnabled(false);
        btOut.setEnabled(false);
        if(inStatus.equals("0")){
            btIn.setEnabled(true);
            btOut.setEnabled(false);
        } else if(outStatus.equals("0")){
            btIn.setEnabled(false);
            btOut.setEnabled(true);
        }
    }

    public void checkAttendance(String search){
        AlertsModule alert = new AlertsModule(AttendanceActivity.this);
        try {
            post = new strEventDetails(search);
            ApiClientInterface clientInterface = ApiInterface.getClient(host).create(ApiClientInterface.class);
            Call<List<strEventDetails>> call = clientInterface.getViewerAttendanceList(post);

            call.enqueue(new Callback<List<strEventDetails>>() {
                @Override
                public void onResponse(Call<List<strEventDetails>> call, Response<List<strEventDetails>> response) {
                    try {
                        if(response.body() != null){
                            List<strEventDetails> viewerList = response.body();
                            dataList.clear();
                            for(int i = 0; i<viewerList.size(); i++){
                                String getName, getSeatNo, getInTime, getOutTime, getInStat, getOutStat;
                                getName = viewerList.get(i).getFullName();
                                getSeatNo = viewerList.get(i).getSeat_no();
                                getInTime = viewerList.get(i).getInTime();
                                getOutTime = viewerList.get(i).getOutTime();
                                getInStat = viewerList.get(i).getInStatServer();
                                getOutStat = viewerList.get(i).getOutStatServer();

                                strEventDetails data = new strEventDetails(getName,
                                        getSeatNo,
                                        getInTime,getOutTime,
                                        getInStat,getOutStat);
                                dataList.add(data);
                            }
                        }
                        loadData();
                    }catch (Exception e){
                        alert.variableErrorMessage(e.getMessage());
                        btIn.setEnabled(false);
                        btOut.setEnabled(false);
                    }
                }

                @Override
                public void onFailure(Call<List<strEventDetails>> call, Throwable t) {
                    alert.variableErrorMessage(t.getMessage());
                }
            });
        }
        catch (Exception e){
            alert.variableErrorMessage(e.getMessage());
        }
    }

    //-- Load Data from network
    private void loadData() {
        //dummyData();
        dataList.removeAll(Collections.singleton(null));
        adapter = new attendanceDataAdapter(dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listAttendanceData.removeAllViews();
        listAttendanceData.removeAllViewsInLayout();
        listAttendanceData.setLayoutManager(layoutManager);
        listAttendanceData.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}