package event.prototype.app.eventmanagement.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import event.prototype.app.eventmanagement.data.email.EmailDao
import event.prototype.app.eventmanagement.data.event.EventDao
import event.prototype.app.eventmanagement.data.phone.PhoneDao
import event.prototype.app.eventmanagement.model.email.EmailContactModel
import event.prototype.app.eventmanagement.model.event.EventModel
import event.prototype.app.eventmanagement.model.event.EventTypeConverter
import event.prototype.app.eventmanagement.model.phone.PhoneContactModel

@Database(entities =
[
    EventModel::class,
    PhoneContactModel::class,
    EmailContactModel::class
], version = 1, exportSchema = true)
@TypeConverters(EventTypeConverter::class)
abstract class MainDataBase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun phoneDao(): PhoneDao
    abstract fun emailDao(): EmailDao

}