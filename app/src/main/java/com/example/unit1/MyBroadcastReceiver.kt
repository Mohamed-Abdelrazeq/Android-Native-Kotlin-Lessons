package com.example.unit1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state",false ) ?: return
        if (isAirplaneModeEnabled){
            Toast.makeText(context,"Airplane Enabled",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context,"Airplane Disable",Toast.LENGTH_LONG).show()
        }
    }
}