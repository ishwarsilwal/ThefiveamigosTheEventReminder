package event.prototype.app.eventmanagement.home.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import event.prototype.app.eventmanagement.utils.Event
import javax.inject.Inject


class HomeViewModel @Inject constructor() : ViewModel() , Observable {

    companion object {
        const val TAG = "HomeViewModel"
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    private val _navigateToAdd: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val _navigateToUpdate: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val _navigateToDelete: MutableLiveData<Event<Unit>> = MutableLiveData()
    private val _navigateToView: MutableLiveData<Event<Unit>> = MutableLiveData()


    val navigateToAddObserver: LiveData<Event<Unit>>
        get() = _navigateToAdd

    val navigateToUpdateObserver: LiveData<Event<Unit>>
        get() = _navigateToUpdate

    val navigateToDeleteObserver: LiveData<Event<Unit>>
        get() = _navigateToDelete

    val navigateToViewObserver: LiveData<Event<Unit>>
        get() = _navigateToView


    fun onNavigateToAdd(){
        _navigateToAdd.value = Event(Unit)
    }

    fun onNavigateToUpdate(){
        _navigateToUpdate.value = Event(Unit)
    }

    fun onNavigateToDelete(){
        _navigateToDelete.value = Event(Unit)
    }

    fun onNavigateToView(){
        _navigateToView.value = Event(Unit)
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