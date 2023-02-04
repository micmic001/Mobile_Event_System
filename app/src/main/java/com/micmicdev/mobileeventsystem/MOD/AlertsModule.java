package com.micmicdev.mobileeventsystem.MOD;

import android.content.Context;
import android.content.ContextWrapper;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AlertsModule extends ContextWrapper {
    protected Context context;

    public AlertsModule(Context base) {
        super(base);
    }

    public void successAlert(){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("You clicked the button!")
                .show();
    }

    public void errorInvalidTicket(){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Invalid Ticket!")
                .setContentText("Ticket is invalid or damaged.")
                .show();
    }

    public void generalMessage(String message){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(message)
                .show();
    }

    public void successRegistration(){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Viewer Registered")
                .show();
    }
    public void variableSuccessMessage(String message){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success!")
                .setContentText(message)
                .show();
    }

    public void variableErrorMessage(String message){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error!")
                .setContentText(message)
                .show();
    }

    public void noScannedTicket(String message){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("No Ticket Scanned!")
                .setContentText(message)
                .show();
    }

}
