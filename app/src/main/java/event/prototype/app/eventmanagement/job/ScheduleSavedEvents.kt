package event.prototype.app.eventmanagement.job

import android.util.Log
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import com.evernote.android.job.JobManager
import event.prototype.app.eventmanagement.model.event.EventModel
import java.text.SimpleDateFormat
import java.util.*


object ScheduleSavedEvents {

    private var mLastJobId: Int = 0

    fun scheduleJob(event: EventModel, isReschedule : Boolean) : Boolean {
        val extras = PersistableBundleCompat()
        extras.putString("event_id", event.id)
        extras.putString("event_message", event.eventWish + " ( "+ event.eventType.name.capitalize() +" ) ")

        val myDate = event.eventDate + " " + event.eventTime
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val date = sdf.parse(myDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        val millis = calendar.timeInMillis

        val alarmTime =  millis - System.currentTimeMillis()

        if(alarmTime <= 0){
            return false
        }

        Log.d("AlarmSchedule", "Event Time : "+millis.toString())
        Log.d("AlarmSchedule", "Current Time : "+System.currentTimeMillis())
        Log.d("AlarmSchedule", "Subtracted : "+ alarmTime.toString())

        mLastJobId = JobRequest.Builder(event.id)
            .setExtras(extras)
            .setUpdateCurrent(true)
            .setExact(alarmTime)
            .build()
            .schedule()

        return true

    }

     fun cancelJobForEvent(eventId: String) {
        JobManager.instance().cancelAllForTag(eventId)
    }

}