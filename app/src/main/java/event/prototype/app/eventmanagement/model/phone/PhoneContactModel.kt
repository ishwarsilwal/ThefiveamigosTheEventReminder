package event.prototype.app.eventmanagement.model.phone

import android.arch.persistence.room.*
import android.os.Parcelable
import event.prototype.app.eventmanagement.model.event.EventModel
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Entity(
    tableName = "phone_contact_tbl",
    foreignKeys = [ForeignKey(entity = EventModel::class, parentColumns = ["id"], childColumns = ["event_id"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey .CASCADE)],
    indices = [Index("event_id")]
)
data class PhoneContactModel (

    @ColumnInfo(name = "event_id")
    var eventId: String = "",

    @ColumnInfo(name = "phone_name")
    var name : String = "",

    @ColumnInfo(name = "phone_number")
    var phoneNumber : String = "",

    @Ignore
    var isAdded : Boolean = false

): Serializable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var phoneId: Long = 0

    override fun toString(): String {
        return "$name $phoneNumber $isAdded"
    }
}