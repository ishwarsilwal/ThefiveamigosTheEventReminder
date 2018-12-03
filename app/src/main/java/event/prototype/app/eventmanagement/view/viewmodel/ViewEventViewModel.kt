package event.prototype.app.eventmanagement.view.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import event.prototype.app.eventmanagement.model.event.EventAllDetails
import event.prototype.app.eventmanagement.model.event.EventModel
import event.prototype.app.eventmanagement.utils.runOnIoThread
import event.prototype.app.eventmanagement.view.ViewEventRepository
import javax.inject.Inject


class ViewEventViewModel @Inject constructor(private val viewEventRepository: ViewEventRepository) : ViewModel() , Observable {

    companion object {
        const val TAG = "ViewEventViewModel"
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    var eventListLiveData : MediatorLiveData<List<EventAllDetails>> = MediatorLiveData()

    init {
        eventListLiveData.addSource(viewEventRepository.getAllEventWithDetails) {
            eventListLiveData.value = it
        }
    }

    fun deleteEvent(eventModel: EventModel){
        runOnIoThread {
            viewEventRepository.deleteEvent(eventModel)
        }
    }

    fun rescheduleEvent(eventModel: EventAllDetails){
        runOnIoThread {
            viewEventRepository.rescheduleEvent(eventModel.event!!)
        }
    }

    fun getParticularEvent(eventId : String) = viewEventRepository.getParticularEventDetail(eventId)


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