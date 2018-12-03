package event.prototype.app.eventmanagement.alarm

import event.prototype.app.eventmanagement.data.event.EventDao
import javax.inject.Inject


class AlarmRepository @Inject constructor(private val eventDao: EventDao) {

    fun getParticularEventDetail(eventId : String) = eventDao.getParticularEventWIthDetails(eventId)

}