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
}
