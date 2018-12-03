package event.prototype.app.eventmanagement.model.event

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import android.os.Parcelable
import event.prototype.app.eventmanagement.model.email.EmailContactModel
import event.prototype.app.eventmanagement.model.phone.PhoneContactModel
import java.io.Serializable


class EventAllDetails : Serializable {

    @Embedded
    var event: EventModel? = null

    @Relation(parentColumn = "id",
    entityColumn = "event_id")
    var phoneContactList: List<PhoneContactModel> = mutableListOf()

    @Relation(parentColumn = "id",
        entityColumn = "event_id")
    var emailContactList: List<EmailContactModel> = mutableListOf()

}