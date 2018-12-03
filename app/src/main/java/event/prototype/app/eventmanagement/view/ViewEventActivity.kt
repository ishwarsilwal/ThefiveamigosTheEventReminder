package event.prototype.app.eventmanagement.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import  event.prototype.app.eventmanagement.job.ScheduleSavedEvents
import event.prototype.app.eventmanagement.BaseActivity
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.databinding.ActivityViewEventBinding
import event.prototype.app.eventmanagement.di.MainComponent
import event.prototype.app.eventmanagement.model.event.EventType
import event.prototype.app.eventmanagement.addevent.AddEventActivity
import event.prototype.app.eventmanagement.model.event.EventAllDetails
import event.prototype.app.eventmanagement.view.recyclerview.EventListAdapter
import event.prototype.app.eventmanagement.view.viewmodel.ViewEventViewModel
import event.prototype.app.eventmanagement.utils.Constants
import kotlinx.android.synthetic.main.activity_view_event.*
import javax.inject.Inject

class ViewEventActivity : BaseActivity(), EventListAdapter.OnEventClicked {

    override fun setOnEventRescheduleListener(event: EventAllDetails) {
        viewEventViewModel.rescheduleEvent(event)
        viewEventViewModel.getParticularEvent(event.event?.id!!).observe(this, Observer {
            ScheduleSavedEvents.scheduleJob(it?.event!!, true)
        })
    }

    override fun setOnEventDeleteListener(event: EventAllDetails) {
        ScheduleSavedEvents.cancelJobForEvent(event.event?.id!!)
        viewEventViewModel.deleteEvent(event.event!!)
    }

    override fun setOnEventUpdateListener(event: EventAllDetails) {
        val intent = Intent(this, AddEventActivity::class.java)
        intent.putExtra(Constants.EVENTALLDETAILS,event)
        intent.putExtra(Constants.FORMTYPE, Constants.UPDATE)
        startActivity(intent)
    }

    override fun setOnEventClickedListener(event: EventAllDetails) {

    }

    private lateinit var viewEventViewModel: ViewEventViewModel

    private lateinit var activityViewEventBinding : ActivityViewEventBinding

    private lateinit var adapter: EventListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun configureDependencies(mainComponent: MainComponent) {
        mainComponent.viewEventSubComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewEventViewModel = ViewModelProviders.of(this, viewModelFactory)[ViewEventViewModel::class.java]
        activityViewEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_event)
        activityViewEventBinding.viewEventViewModel = viewEventViewModel

        initialize()

        viewEventViewModel.eventListLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun initialize() {
        adapter = EventListAdapter()
        adapter.onEventClicked = this
        eventListRv.adapter = adapter
    }
}
