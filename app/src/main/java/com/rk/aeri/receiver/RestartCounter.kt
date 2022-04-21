package com.rk.aeri.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rk.aeri.eventbus.StepSendBus
import com.rk.aeri.sharedoperations.SharedStep
import com.rk.aeri.serviceoperations.AeriCounter
import org.greenrobot.eventbus.EventBus
import java.util.*


class RestartCounter : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val service = Intent(context, AeriCounter::class.java).apply {

            this.putExtra("restart", "restart")
        }

        context.startService(service)

        EventBus.getDefault().postSticky ( StepSendBus (0 ) )

        val calendar = Calendar.getInstance()

        SharedStep ( context ).saveStep ( context , true , calendar.get(Calendar.DAY_OF_MONTH) , (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR) , null )
    }
}