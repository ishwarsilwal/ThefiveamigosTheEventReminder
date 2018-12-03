package event.prototype.app.eventmanagement.view.recyclerview

import android.support.v7.util.DiffUtil
import event.prototype.app.eventmanagement.model.event.EventAllDetails


class EventListDiffCallback : DiffUtil.ItemCallback<EventAllDetails>() {

    override fun areItemsTheSame(oldItem: EventAllDetails, newItem: EventAllDetails): Boolean {
        return oldItem.event?.id == newItem.event?.id
    }

    override fun areContentsTheSame(oldItem: EventAllDetails, newItem: EventAllDetails): Boolean {
        return oldItem.event == newItem.event
    }


}