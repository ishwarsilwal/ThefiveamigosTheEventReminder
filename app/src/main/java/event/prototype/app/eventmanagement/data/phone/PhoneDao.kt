package event.prototype.app.eventmanagement.data.phone

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import event.prototype.app.eventmanagement.model.phone.PhoneContactModel


@Dao
interface PhoneDao {

    @Insert
    fun insertPhone(phoneContactModel: PhoneContactModel)

    @Update
    fun updatePhone(phoneContactModel: PhoneContactModel)

    @Query("DELETE FROM phone_contact_tbl WHERE event_id = :eventId")
    fun delete(eventId : String)

}