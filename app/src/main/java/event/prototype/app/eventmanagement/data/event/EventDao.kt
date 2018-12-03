package event.prototype.app.eventmanagement.data.event

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import event.prototype.app.eventmanagement.model.event.EventAllDetails
import event.prototype.app.eventmanagement.model.event.EventModel

@Dao
interface EventDao {

    @Insert
    fun insertEvent(eventModel: EventModel)

    @Query("Select * from event_tbl")
    fun findAllEvent() : LiveData<List<EventModel>>

    @Transaction
    @Query("SELECT * FROM event_tbl")
    fun getAllEventWIthDetails() : LiveData<List<EventAllDetails>>

    @Transaction
    @Query("SELECT * FROM event_tbl where event_tbl.id = :eventId")
    fun getParticularEventWIthDetails(eventId : String) : LiveData<EventAllDetails>


    @Query("UPDATE event_tbl SET event_completed = 1 where id = :eventId")
    fun updateEventAsCompleted(eventId : String)

    @Query("UPDATE event_tbl SET event_completed = 0  where id = :eventId")
    fun updateEventAsNew(eventId : String)

    @Query("UPDATE event_tbl SET  event_date = :eventDate where id = :eventId")
    fun updateEventTime(eventId : String,  eventDate : String)

    @Delete
    fun deleteEvent(eventModel: EventModel)

    @Update
    fun updateEvent(eventModel: EventModel)
}