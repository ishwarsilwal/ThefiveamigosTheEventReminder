package event.prototype.app.eventmanagement.update

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import event.prototype.app.eventmanagement.BaseActivity
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.addevent.viewmodel.AddEventViewModel
import event.prototype.app.eventmanagement.databinding.ActivityUpdateEventBinding
import event.prototype.app.eventmanagement.di.MainComponent
import event.prototype.app.eventmanagement.model.event.EventAllDetails
import event.prototype.app.eventmanagement.model.event.EventType
import event.prototype.app.eventmanagement.update.viewmodel.UpdateEventViewModel
import event.prototype.app.eventmanagement.utils.Constants
import javax.inject.Inject

class UpdateEventActivity : BaseActivity() {

    private lateinit var updateEventViewModel : UpdateEventViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var activityUpdateEventBinding : ActivityUpdateEventBinding

    override fun configureDependencies(mainComponent: MainComponent) {
        mainComponent.updateEventSubComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val eventAllDetails = intent.getSerializableExtra(Constants.EVENTALLDETAILS) as EventAllDetails

        updateEventViewModel = ViewModelProviders.of(this, viewModelFactory)[UpdateEventViewModel::class.java]

        updateEventViewModel.eventAllDetails = eventAllDetails

        val eventType = eventAllDetails.event?.eventType

        activityUpdateEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_event)
        activityUpdateEventBinding.apply {
            appBarLayout?.toolBar.apply {
                this?.title = if(eventType == EventType.BIRTHDAY) getString(R.string.add_birthday) else if(eventType == EventType.ANNIVERSARY) getString(
                    R.string.add_anniversary) else getString(R.string.add_custom)
                this?.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
            updateEventViewModel = this@UpdateEventActivity.updateEventViewModel
        }

        updateEventViewModel.eventWish = eventAllDetails.event?.eventWish!!


//        updateEventViewModel.eventTime = eventAllDetails.event?.eventTime!!
//        updateEventViewModel.eventDate = eventAllDetails.event?.eventDate!!
//        updateEventViewModel.eventWish = eventAllDetails.event?.eventWish!!

    }
}
