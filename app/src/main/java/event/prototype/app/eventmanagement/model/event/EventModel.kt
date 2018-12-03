package event.prototype.app.eventmanagement.model.event

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "event_tbl")
data class EventModel (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : String,
    @ColumnInfo(name = "event_type")
    val eventType : EventType,
    @ColumnInfo(name = "event_wish")
    val eventWish : String,
    @ColumnInfo(name = "event_date")
    val eventDate : String,
    @ColumnInfo(name = "event_time")
    val eventTime : String,
    @ColumnInfo(name = "event_repeat")
    val eventRepeat : Boolean,
    @ColumnInfo(name = "event_completed")
    val eventCompleted : Boolean
): Serializable