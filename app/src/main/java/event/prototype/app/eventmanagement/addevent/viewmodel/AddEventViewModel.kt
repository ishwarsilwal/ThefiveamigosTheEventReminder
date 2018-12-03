package event.prototype.app.eventmanagement.addevent.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import android.util.Log
import event.prototype.app.eventmanagement.BR
import event.prototype.app.eventmanagement.addevent.AddEventRepository
import event.prototype.app.eventmanagement.model.email.EmailContactModel
import event.prototype.app.eventmanagement.model.event.EventAllDetails
import event.prototype.app.eventmanagement.model.event.EventModel
import event.prototype.app.eventmanagement.model.event.EventType
import event.prototype.app.eventmanagement.model.phone.PhoneContactModel
import event.prototype.app.eventmanagement.utils.Event
import event.prototype.app.eventmanagement.utils.runOnIoThread
import event.prototype.app.eventmanagement.job.ScheduleSavedEvents
import java.util.*
import javax.inject.Inject


class AddEventViewModel @Inject constructor(private val addEventRepository: AddEventRepository) : ViewModel(),
    Observable {

    companion object {
        const val TAG = "AddEventViewModel"
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    private val _openDatePicker: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val _openTimePicker: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val _onSaveComplete: MutableLiveData<Event<Boolean>> = MutableLiveData()
    private val _onNewEmailListChanged: MutableLiveData<EmailContactModel> = MutableLiveData()
    private val _onNewPhoneListChanged: MutableLiveData<PhoneContactModel> = MutableLiveData()


    val openDatePickerObserver: LiveData<Event<Unit>>
        get() = _openDatePicker

    val openTimePickerObserver: LiveData<Event<Unit>>
        get() = _openTimePicker

    val saveCompleteObserver: LiveData<Event<Boolean>>
        get() = _onSaveComplete

    val newEmailListObserver: LiveData<EmailContactModel>
        get() = _onNewEmailListChanged

    val newPhoneListObserver: LiveData<PhoneContactModel>
        get() = _onNewPhoneListChanged

     lateinit var eventAllDetails: EventAllDetails
        var update: Boolean = false

    var eventPhone: String = ""
        @Bindable get
        set(value) {
            if (!value.isBlank()) {
                if (value[value.length - 1].toString() == ";") {
                    val phoneString = value.replace(";", "")
                    val newPhoneContactModel = PhoneContactModel(phoneNumber = phoneString, isAdded = true)
                    field = ""
                    _onNewPhoneListChanged.value = newPhoneContactModel
                } else {
                    field = value
                }
            }
            callbacks.notifyCallbacks(this, BR.eventPhone, null)
        }

    var phoneContactModel: PhoneContactModel = PhoneContactModel()
        @Bindable get
        set(value) {
            field = value
            callbacks.notifyCallbacks(this, BR.phoneContactModel, null)
        }

    var phoneContactList: MutableList<PhoneContactModel> = mutableListOf()
        @Bindable("phoneContactModel") get() {
            if (phoneContactModel.isAdded) {
                field.add(phoneContactModel)
            } else {
                if (field.size != 0) {
                    field.removeAll {
                        phoneContactModel.phoneNumber == it.phoneNumber
                    }
                }
            }
            return field
        }

    var eventEmail: String = ""
        @Bindable get
        set(value) {
            if (!value.isBlank()) {
                if (value[value.length - 1].toString() == ";") {
                    val emailString = value.replace(";", "")
                    val newEmailContactModel = EmailContactModel(emailAddress = emailString, isAdded = true)
                    field = ""
                    _onNewEmailListChanged.value = newEmailContactModel
                } else {
                    field = value
                }
            }
            callbacks.notifyCallbacks(this, BR.eventEmail, null)
        }


    var emailModel: EmailContactModel = EmailContactModel()
        @Bindable get
        set(value) {
            field = value
            callbacks.notifyCallbacks(this, BR.emailModel, null)
        }

    var emailContactList: MutableList<EmailContactModel> = mutableListOf()
        @Bindable("emailModel") get() {
            if (emailModel.isAdded) {
                field.add(emailModel)
            } else {
                if (field.size != 0) {
                    field.removeAll {
                        emailModel.emailAddress == it.emailAddress
                    }
                }
            }
            return field
        }

    var eventType: EventType = EventType.ANNIVERSARY
        @Bindable get
        set(value) {
            field = value
            callbacks.notifyCallbacks(this, BR.eventType, null)
        }

    var eventWish: String = ""
        @Bindable get
        set(value) {
            field = value
            callbacks.notifyCallbacks(this, BR.eventWish, null)
        }

    var eventDate: String = ""
        @Bindable get
        set(value) {
            field = value
            callbacks.notifyCallbacks(this, BR.eventDate, null)
        }

    fun onEventDateClicked() {
        _openDatePicker.value = Event(Unit)
    }

    var eventTime: String = ""
        @Bindable get
        set(value) {
            field = value
            callbacks.notifyCallbacks(this, BR.eventTime, null)
        }


    fun onEventTimeClicked() {
        _openTimePicker.value = Event(Unit)
    }


    var enableSaveButton: Boolean = false
        @Bindable("phoneContactList", "eventWish", "eventDate", "eventTime", "eventEmail") get() {
            if (!eventEmail.isBlank()) {
                Log.d("EmailTestButton ", eventEmail)
                Log.d("EmailTestButton ", eventEmail[eventEmail.length - 1].toString())
            }
            val isContactAdded = phoneContactList.size != 0
            val isEventWishValid = eventWish.isNotBlank()
            val isEventDateValid = eventDate.isNotBlank()
            val isEventTimeValid = eventTime.isNotBlank()
            return isContactAdded && isEventWishValid && isEventDateValid && isEventTimeValid
        }

    fun onSaveButtonClicked() {
        Log.d(TAG, phoneContactList.toString())
        runOnIoThread {
            if(!update) {
                val event = EventModel(
                    UUID.randomUUID().toString(),
                    eventType,
                    eventWish,
                    eventDate,
                    eventTime,
                    false,
                    false
                )
                addEventRepository.addEventWithDetails(event, phoneContactList.distinct(), emailContactList.distinct())
                _onSaveComplete.postValue(Event( ScheduleSavedEvents.scheduleJob(event, false)))
            }else{
                val event = EventModel(
                    eventAllDetails.event?.id!!,
                    eventType,
                    eventWish,
                    eventDate,
                    eventTime,
                    false,
                    false
                )
                addEventRepository.updateEventWithDetails(event, phoneContactList.distinct(), emailContactList.distinct())
                ScheduleSavedEvents.scheduleJob(event, false)
                _onSaveComplete.postValue(Event(ScheduleSavedEvents.scheduleJob(event, false)))
            }
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun onCleared() {
        super.onCleared()
    }

}