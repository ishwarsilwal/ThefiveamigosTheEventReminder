package event.prototype.app.eventmanagement.job

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.evernote.android.job.Job
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.alarm.AlarmActivity
import event.prototype.app.eventmanagement.home.HomeActivity
import event.prototype.app.eventmanagement.utils.Constants
import java.util.*


class EventJob(private val eventGuid: String) : Job() {

    companion object {
        val TAG = "job_event_tag"
    }

    override fun onRunJob(params: Job.Params): Job.Result {
        try {

            Log.d("AlarmSchedule", params.tag)

//            val smsManager = SmsManager.getDefault()
//            smsManager.sendTextMessage("+9779851184633", null, "This is test", null, null)

            val pendingIntent = PendingIntent.getActivity(context, 0, Intent(context, HomeActivity::class.java), 0)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(TAG, "Job Demo", NotificationManager.IMPORTANCE_LOW)
                channel.description = "Job demo job"
                context.getSystemService(NotificationManager::class.java)!!.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(context,
                TAG
            )
                .setContentTitle("Event Management")
                .setContentText(params.extras.getString("event_message","No event id found"))
                .setAutoCancel(true)
                .setChannelId(TAG)
                .setSound(null)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowWhen(true)
                .setColor(Color.GREEN)
                .setLocalOnly(true)
                .build()

            NotificationManagerCompat.from(context).notify(Random().nextInt(), notification)

            val alarmActivity = Intent(context.applicationContext, AlarmActivity::class.java)
            alarmActivity.putExtra(Constants.EVENTID,params.extras.getString("event_id","") )
            alarmActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(alarmActivity)

            return Job.Result.SUCCESS

        } catch (e: Exception) {
            Log.d(TAG, e.localizedMessage)

            return Job.Result.FAILURE
        }
    }
}