package event.prototype.app.eventmanagement.eventtypeselection

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import event.prototype.app.eventmanagement.BaseActivity
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.addevent.AddEventActivity
import event.prototype.app.eventmanagement.databinding.ActivityEventTypeBinding
import event.prototype.app.eventmanagement.di.MainComponent
import event.prototype.app.eventmanagement.eventtypeselection.viewmodel.EventTypeViewModel
import event.prototype.app.eventmanagement.model.event.EventType
import event.prototype.app.eventmanagement.utils.EventObserver
import javax.inject.Inject
import event.prototype.app.eventmanagement.utils.Constants


class EventTypeActivity : BaseActivity() {

    private lateinit var eventTypeViewModel: EventTypeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var  activityEventTypeBinding: ActivityEventTypeBinding


    override fun configureDependencies(mainComponent: MainComponent) {
        mainComponent.eventTypeSubComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventTypeViewModel = ViewModelProviders.of(this, viewModelFactory)[EventTypeViewModel::class.java]
        activityEventTypeBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_type)
        activityEventTypeBinding.eventTypeViewModel = eventTypeViewModel

        eventTypeViewModel.navigateToAnniversaryObserver.observe(this, EventObserver{
            val intent = Intent(this, AddEventActivity::class.java)
            intent.putExtra(Constants.FORMTYPE, Constants.ADD)
            intent.putExtra(Constants.EVENTTYPE,EventType.ANNIVERSARY)
            startActivity(intent)
        })

        eventTypeViewModel.navigateToBirthdayObserver.observe(this, EventObserver{
            val intent = Intent(this, AddEventActivity::class.java)
            intent.putExtra(Constants.FORMTYPE, Constants.ADD)
            intent.putExtra(Constants.EVENTTYPE,EventType.BIRTHDAY)
            startActivity(intent)
        })

        eventTypeViewModel.navigateToCustomObserver.observe(this, EventObserver{
            val intent = Intent(this, AddEventActivity::class.java)
            intent.putExtra(Constants.FORMTYPE, Constants.ADD)
            intent.putExtra(Constants.EVENTTYPE,EventType.CUSTOM)
            startActivity(intent)
        })

    }
}
