package event.prototype.app.eventmanagement.alarm.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import event.prototype.app.eventmanagement.addevent.AddEventRepository
import event.prototype.app.eventmanagement.alarm.AlarmRepository
import event.prototype.app.eventmanagement.data.event.EventDao
import event.prototype.app.eventmanagement.model.event.EventAllDetails
import event.prototype.app.eventmanagement.model.event.EventModel
import javax.inject.Inject


class AlarmViewModel @Inject constructor(private val alarmRepository: AlarmRepository, private val eventDao: EventDao) : ViewModel(),
    Observable {

    companion object {
        const val TAG = "AlarmViewModel"
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }


    val eventMutableData : MutableLiveData<EventAllDetails> =  MutableLiveData()


    fun getParticularEvent(eventId : String) = alarmRepository.getParticularEventDetail(eventId)


    fun updateEventAsCompleted(event : EventModel) = eventDao.updateEventAsCompleted(event.id)

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