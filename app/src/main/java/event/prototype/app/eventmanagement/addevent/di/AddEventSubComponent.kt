package event.prototype.app.eventmanagement.addevent.di

import dagger.Subcomponent
import event.prototype.app.eventmanagement.addevent.AddEventActivity


@Subcomponent(modules = [AddEventModule::class])
interface AddEventSubComponent {

     fun inject(addEventActivity: AddEventActivity)
}