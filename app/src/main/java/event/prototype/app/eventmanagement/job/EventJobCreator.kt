package event.prototype.app.eventmanagement.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator


class EventJobCreator() : JobCreator {

    override fun create(tag: String): Job? {
        return when (tag) {
            EventJob.TAG -> EventJob(
                "test"
            )
            else -> EventJob(tag)
        }
    }
}