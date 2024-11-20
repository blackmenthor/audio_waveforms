package com.simform.audio_waveforms

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AudioRecorderService: Service() {

    private lateinit var notification: Notification
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.setDescription(CHANNEL_DESCRIPTION)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val activityPackage = intent?.extras?.getString("ACTIVITY_NAME")

        val intent1 = Intent(this, Class.forName(activityPackage!!))
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
        .setContentTitle("Recording in Progress")
            .setContentText("Tap to stop")
            .setSmallIcon(R.drawable.bash_logo)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setOngoing(true)

        notification = builder.build()
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@AudioRecorderService,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@with
            }
            // notificationId is a unique int for each notification that you must define.
            notify(NOTIFICATION_ID, builder.build())
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        with(NotificationManagerCompat.from(this)) {
            cancel(com.simform.audio_waveforms.AudioRecorderService.Companion.NOTIFICATION_ID)
        }

    }
    override fun onBind(intent: Intent?): IBinder? {
        Log.e("ONGGO", "ONBIND")
        TODO("Not yet implemented")
    }

    companion object {
        val CHANNEL_ID = "AUDIO_RECORDER_WAVEFORM"
        val CHANNEL_NAME = "Audio Recorder"
        val CHANNEL_DESCRIPTION = "Audio Recorder"
        val NOTIFICATION_ID = 888
    }
}