package com.micmicdev.mobileeventsystem.MOD;

import android.bluetooth.BluetoothAdapter;

public class GetDeviceNameModule {
    //-----------------------------------------
    //Connects to bluetooth adapter module
    //and gets the set device name.
    //-----------------------------------------
    public static String getDeviceName(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        String name = mBluetoothAdapter.getName();
        if(name == null){
            System.out.println("Name is null!");
            name = mBluetoothAdapter.getAddress();
        }
        return name;
    }
}
