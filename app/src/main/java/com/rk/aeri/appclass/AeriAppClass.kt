package com.rk.aeri.appclass;

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import com.rk.aeri.receiver.RestartCounter
import java.util.*

class AeriAppClass : Application() {

    override fun onCreate() {

        if ( Build.VERSION.SDK_INT >= 26 ) {

            val channel = NotificationChannel("step-1", "Adım", NotificationManager.IMPORTANCE_LOW ).apply {

                setShowBadge(false)
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0

        val intent = Intent(this, RestartCounter::class.java)

        val pendingIntent = if ( Build.VERSION.SDK_INT >= 23 ) {

            PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT )

        } else {

            PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT )
        }

        val alarm = getSystemService(ALARM_SERVICE) as AlarmManager

        alarm.setInexactRepeating(AlarmManager.RTC, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        super.onCreate()
    }
}
