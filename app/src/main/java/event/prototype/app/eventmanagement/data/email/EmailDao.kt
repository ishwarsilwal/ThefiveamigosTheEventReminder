package event.prototype.app.eventmanagement.data.email

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import event.prototype.app.eventmanagement.model.email.EmailContactModel


@Dao
interface EmailDao {

    @Insert
    fun insertEmail(emailContactModel: EmailContactModel)

    @Update
    fun updateEmail(emailContactModel: EmailContactModel)

    @Query("DELETE FROM email_contact_tbl WHERE event_id = :eventId")
    fun delete(eventId : String)

}