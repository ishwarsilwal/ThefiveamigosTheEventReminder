package event.prototype.app.eventmanagement.view.recyclerview

import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.databinding.EventListRowBinding
import event.prototype.app.eventmanagement.model.event.EventAllDetails

class EventListAdapter() : ListAdapter<EventAllDetails,EventListAdapter.ViewHolder>(EventListDiffCallback()) {

     lateinit var onEventClicked: OnEventClicked

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): ViewHolder {
        return ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.event_list_row, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { event ->
            with(holder) {
                itemView.tag = event
                bind(onEventClicked(event),onEventDeleteClicked(event),onEventUpdateClicked(event),onEventRescheduleClicked(event),event)
            }
        }
    }

    private fun onEventClicked(event: EventAllDetails): View.OnClickListener {
        return View.OnClickListener {
            onEventClicked.setOnEventClickedListener(event)
        }
    }

    private fun onEventDeleteClicked(event: EventAllDetails): View.OnClickListener {
        return View.OnClickListener {
            onEventClicked.setOnEventDeleteListener(event)
        }
    }

    private fun onEventRescheduleClicked(event: EventAllDetails): View.OnClickListener {
        return View.OnClickListener {
            onEventClicked.setOnEventRescheduleListener(event)
        }
    }

    private fun onEventUpdateClicked(event: EventAllDetails): View.OnClickListener {
        return View.OnClickListener {
            onEventClicked.setOnEventUpdateListener(event)
        }
    }

    class ViewHolder(
            private val binding: EventListRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(onEventClicked: View.OnClickListener,onEventDeleteClicked: View.OnClickListener,onEventUpdateClicked: View.OnClickListener,onEventRescheduleClicked: View.OnClickListener,event: EventAllDetails) {
            with(binding) {
                eventListViewModel = EventListViewModel(
                        itemView.context,
                        event
                )
                eventClickListener = onEventClicked
                eventDeleteClickListener = onEventDeleteClicked
                eventUpdateClickListener = onEventUpdateClicked
                eventReScheduleClickListener =  onEventRescheduleClicked
                executePendingBindings()
            }
        }
    }

     interface OnEventClicked{
        fun setOnEventClickedListener(event: EventAllDetails)
         fun setOnEventDeleteListener(event: EventAllDetails)
         fun setOnEventUpdateListener(event: EventAllDetails)
         fun setOnEventRescheduleListener(event: EventAllDetails)
    }

}