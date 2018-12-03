package event.prototype.app.eventmanagement.model.email

import android.arch.persistence.room.*
import event.prototype.app.eventmanagement.model.event.EventModel
import kotlinx.android.parcel.IgnoredOnParcel
import java.io.Serializable


@Entity(
    tableName = "email_contact_tbl",
    foreignKeys = [ForeignKey(
        entity = EventModel::class, parentColumns = ["id"], childColumns = ["event_id"], onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("event_id")]
)
data class EmailContactModel(

    @ColumnInfo(name = "event_id")
    var eventId: String = "",

    @ColumnInfo(name = "emailaddress")
    var emailAddress: String = "",

    @Ignore
    var isAdded: Boolean = false

) : Serializable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var emailId: Long = 0

    override fun toString(): String {
        return "$emailAddress"
    }
}