package com.rk.aeri.serviceoperations

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rk.aeri.R
import com.rk.aeri.eventbus.StepSendBus
import com.rk.aeri.sharedoperations.SharedStep
import com.rk.aeri.sharedoperations.SharedToast
import org.greenrobot.eventbus.EventBus
import java.util.*

class AeriCounter : Service() , SensorEventListener {

    private var step = 0
    private lateinit var manager: SensorManager
    private lateinit var calendar: Calendar
    private lateinit var stepSensor: Sensor
    private lateinit var wakeLock : PowerManager.WakeLock

    @SuppressLint("InvalidWakeLockTag")
    override fun onCreate() {

        notification (this , "step-1","Adımlar : 0",1 )

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if ( manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {

            stepSensor = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        } else {

            val check = SharedToast( this ).getTarget()

            if ( check == 0 ) {

                Toast.makeText (this ,"Adım sensörü bulunamadı" , Toast.LENGTH_SHORT ).show()

                SharedToast (this).saveTarget(0 )

            } else {

                SharedToast (this).saveTarget(0 )
            }

            this.stopSelf()
        }

        wakeLock  = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {

            this.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag").apply {

                acquire()
            }
        }

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        calendar = Calendar.getInstance()

        if ( manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null ) {

            manager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        step = if ( intent?.action.toString() == "restart") {

            0

        } else {

            SharedStep(this).getStep( calendar.get(Calendar.DAY_OF_MONTH), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR) )
        }

        notification(this,"step-1","Adımlar : $step",1)

        EventBus.getDefault().postSticky( StepSendBus (step) )

        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if ( event?.sensor == stepSensor ) {

            val step = step++

            notification(this, "step-1", "Adımlar : $step", 1)

            SharedStep(this).saveStep(this, false, calendar.get(Calendar.DAY_OF_MONTH), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR), step )

            EventBus.getDefault().postSticky( StepSendBus (step) )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun notification(context: Context, channel: String, text: String, id: Int) {

        val builder = NotificationCompat.Builder(context, channel).apply {

            setContentText(text)
            setSmallIcon(R.drawable.run)
            priority = NotificationCompat.PRIORITY_LOW
        }

        if ( Build.VERSION.SDK_INT >= 26 ) {

            this.startForeground ( id , builder.build())

        } else {

            NotificationManagerCompat.from ( this ).apply {

                notify ( id , builder.build ())
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {

        return null
    }

    override fun onDestroy() {

        wakeLock.release()

        super.onDestroy()
    }
}