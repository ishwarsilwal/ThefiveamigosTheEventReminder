package event.prototype.app.eventmanagement.view

import android.util.Log
import event.prototype.app.eventmanagement.data.event.EventDao
import event.prototype.app.eventmanagement.model.event.EventModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ViewEventRepository @Inject constructor(private val eventDao: EventDao ) {

    var getAllEventWithDetails = eventDao.getAllEventWIthDetails()
    fun  deleteEvent(eventModel : EventModel) = eventDao.deleteEvent(eventModel)

    fun  rescheduleEvent(eventModel : EventModel){
        val myDate = eventModel.eventDate
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = sdf.parse(myDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.YEAR, 1)

        val updatedDate = sdf.format(calendar.time)

        Log.d("AlarmReschedule", updatedDate)

        eventDao.updateEventTime(eventModel.id, updatedDate)
        eventDao.updateEventAsNew(eventModel.id)
    }

    fun getParticularEventDetail(eventId : String) = eventDao.getParticularEventWIthDetails(eventId)

}