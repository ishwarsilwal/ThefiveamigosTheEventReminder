package event.prototype.app.eventmanagement.addevent

import android.arch.persistence.room.Transaction
import android.util.Log
import event.prototype.app.eventmanagement.data.email.EmailDao
import event.prototype.app.eventmanagement.data.event.EventDao
import event.prototype.app.eventmanagement.data.phone.PhoneDao
import event.prototype.app.eventmanagement.model.email.EmailContactModel
import event.prototype.app.eventmanagement.model.event.EventModel
import event.prototype.app.eventmanagement.model.phone.PhoneContactModel
import javax.inject.Inject


class AddEventRepository @Inject constructor(
    private val eventDao: EventDao,
    private val phoneDao: PhoneDao,
    private val emailDao: EmailDao
) {

    @Transaction
    fun addEventWithDetails(
        eventModel: EventModel,
        phoneContactList: List<PhoneContactModel>,
        emailContactList: List<EmailContactModel>
    ) {
        eventDao.insertEvent(eventModel)
        phoneContactList.forEach { phone ->
            phone.eventId = eventModel.id
            phoneDao.insertPhone(phone)
        }
        emailContactList.forEach { email ->
            email.eventId = eventModel.id
            emailDao.insertEmail(email)
        }
    }

    @Transaction
    fun updateEventWithDetails(
        eventModel: EventModel,
        phoneContactList: List<PhoneContactModel>,
        emailContactList: List<EmailContactModel>
    ) {
        eventDao.updateEvent(eventModel)
        phoneContactList.forEach { phone ->
            Log.d("Update", phone.toString())
            if(phone.phoneId != 0L){
               phoneDao.updatePhone(phone)
            }else{
                phone.eventId = eventModel.id
                phoneDao.insertPhone(phone)
            }

        }
        emailContactList.forEach { email ->
            Log.d("Update", email.toString())
            if(email.emailId != 0L){
                emailDao.updateEmail(email)
            }else{
                email.eventId = eventModel.id
                emailDao.insertEmail(email)
            }
        }
    }
}