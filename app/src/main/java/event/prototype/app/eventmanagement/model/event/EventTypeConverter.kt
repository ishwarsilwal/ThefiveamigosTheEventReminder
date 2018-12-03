package event.prototype.app.eventmanagement.model.event

import android.arch.persistence.room.TypeConverter


class EventTypeConverter {

    @TypeConverter
    fun fromStringToEventType(value: String): EventType {
        return when(value){
            EventType.ANNIVERSARY.name -> EventType.ANNIVERSARY
            EventType.BIRTHDAY.name -> EventType.BIRTHDAY
            EventType.CUSTOM.name -> EventType.CUSTOM
            else -> EventType.CUSTOM
        }
    }

    @TypeConverter
    fun fromEventTypeToString(eventType: EventType): String {
        return eventType.name
    }
}