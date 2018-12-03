package event.prototype.app.eventmanagement.update.di

import dagger.Subcomponent
import event.prototype.app.eventmanagement.addevent.AddEventActivity
import event.prototype.app.eventmanagement.update.UpdateEventActivity


@Subcomponent(modules = [UpdateEventModule::class])
interface UpdateEventSubComponent {

     fun inject(updateEventActivity: UpdateEventActivity)
}