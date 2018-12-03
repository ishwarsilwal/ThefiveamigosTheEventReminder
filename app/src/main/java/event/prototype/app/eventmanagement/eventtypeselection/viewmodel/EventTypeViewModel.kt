package event.prototype.app.eventmanagement.eventtypeselection.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import event.prototype.app.eventmanagement.model.event.EventType
import event.prototype.app.eventmanagement.utils.Event
import javax.inject.Inject


class EventTypeViewModel @Inject constructor() : ViewModel() , Observable {

    companion object {
        const val TAG = "EventTypeViewModel"
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    private val _navigateToBirthday: MutableLiveData<Event<EventType>> = MutableLiveData()
    private val _navigateToAnniversary: MutableLiveData<Event<EventType>> = MutableLiveData()
    private val _navigateToCustom: MutableLiveData<Event<EventType>> = MutableLiveData()

    val navigateToBirthdayObserver: LiveData<Event<EventType>>
        get() = _navigateToBirthday

    val navigateToAnniversaryObserver: LiveData<Event<EventType>>
        get() = _navigateToAnniversary

    val navigateToCustomObserver: LiveData<Event<EventType>>
        get() = _navigateToCustom


    fun onNavigateToBirthday(){
        _navigateToBirthday.value = Event(EventType.BIRTHDAY)
    }

    fun onNavigateToAnniversary(){
        _navigateToAnniversary.value = Event(EventType.ANNIVERSARY)
    }

    fun onNavigateToCustom(){
        _navigateToCustom.value = Event(EventType.CUSTOM)
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