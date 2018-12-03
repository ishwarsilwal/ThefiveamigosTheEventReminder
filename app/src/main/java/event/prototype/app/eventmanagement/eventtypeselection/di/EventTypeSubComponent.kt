package event.prototype.app.eventmanagement.eventtypeselection.di

import dagger.Subcomponent
import event.prototype.app.eventmanagement.eventtypeselection.EventTypeActivity


@Subcomponent(modules = [EventTypeModule::class])
interface EventTypeSubComponent {

     fun inject(eventTypeActivity: EventTypeActivity)
}