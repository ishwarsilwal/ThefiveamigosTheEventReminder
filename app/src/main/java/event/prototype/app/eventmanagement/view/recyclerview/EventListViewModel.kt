package event.prototype.app.eventmanagement.view.recyclerview

import android.content.Context
import android.databinding.ObservableField
import event.prototype.app.eventmanagement.model.email.EmailContactModel
import event.prototype.app.eventmanagement.model.event.EventAllDetails
import event.prototype.app.eventmanagement.model.event.EventType
import event.prototype.app.eventmanagement.model.phone.PhoneContactModel
import event.prototype.app.eventmanagement.R


class EventListViewModel (
        context: Context,
        event: EventAllDetails
){

    val eventType = ObservableField<EventType>(event.event?.eventType)
    val eventWish = ObservableField<String>(event.event?.eventWish)
    val eventDate = ObservableField<String>(event.event?.eventDate)
    val eventTime = ObservableField<String>(event.event?.eventTime)
    val eventNumberList = ObservableField<List<PhoneContactModel>>(event.phoneContactList)
    val eventEmailList = ObservableField<List<EmailContactModel>>(event.emailContactList)
    val eventCompleted = ObservableField<Boolean>(event.event?.eventCompleted)
    val eventRepeat = ObservableField<Boolean>(event.event?.eventRepeat)
    val eventImage = ObservableField<Int>(
        when {
            event.event?.eventType == EventType.BIRTHDAY -> R.drawable.ic_birthday
            event.event?.eventType == EventType.ANNIVERSARY -> R.drawable.ic_wedding
            else -> R.drawable.ic_custom
        }
    )

}