package event.prototype.app.eventmanagement.di

import dagger.Component
import event.prototype.app.eventmanagement.addevent.di.AddEventModule
import event.prototype.app.eventmanagement.addevent.di.AddEventSubComponent
import event.prototype.app.eventmanagement.addevent.viewmodel.AddEventViewModel
import event.prototype.app.eventmanagement.alarm.di.AlarmModule
import event.prototype.app.eventmanagement.alarm.di.AlarmSubComponent
import event.prototype.app.eventmanagement.eventtypeselection.di.EventTypeModule
import event.prototype.app.eventmanagement.eventtypeselection.di.EventTypeSubComponent
import event.prototype.app.eventmanagement.home.di.HomeModule
import event.prototype.app.eventmanagement.home.di.HomeSubComponent
import event.prototype.app.eventmanagement.update.di.UpdateEventModule
import event.prototype.app.eventmanagement.update.di.UpdateEventSubComponent
import event.prototype.app.eventmanagement.view.di.ViewEventModule
import event.prototype.app.eventmanagement.view.di.ViewEventSubComponent
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        HomeModule::class,
        EventTypeModule::class,
        AddEventModule::class,
        ViewEventModule::class,
        UpdateEventModule::class,
        AlarmModule::class
    ]
)
interface MainComponent {

    fun homeSubComponent(): HomeSubComponent
    fun eventTypeSubComponent(): EventTypeSubComponent
    fun addEventSubComponent(): AddEventSubComponent
    fun viewEventSubComponent(): ViewEventSubComponent
    fun updateEventSubComponent(): UpdateEventSubComponent
    fun alarmSubComponent(): AlarmSubComponent

}