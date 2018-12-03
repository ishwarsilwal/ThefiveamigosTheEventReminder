package event.prototype.app.eventmanagement.alarm.di

import dagger.Subcomponent
import event.prototype.app.eventmanagement.addevent.AddEventActivity
import event.prototype.app.eventmanagement.alarm.AlarmActivity


@Subcomponent(modules = [AlarmModule::class])
interface AlarmSubComponent {

     fun inject(alarmActivity: AlarmActivity)
}