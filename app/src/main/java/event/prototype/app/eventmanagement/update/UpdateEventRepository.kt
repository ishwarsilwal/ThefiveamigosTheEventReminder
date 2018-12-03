package event.prototype.app.eventmanagement.update

import event.prototype.app.eventmanagement.data.email.EmailDao
import event.prototype.app.eventmanagement.data.event.EventDao
import event.prototype.app.eventmanagement.data.phone.PhoneDao
import javax.inject.Inject


class UpdateEventRepository @Inject constructor(private val eventDao: EventDao, private val phoneDao: PhoneDao, private val emailDao: EmailDao)  {
}