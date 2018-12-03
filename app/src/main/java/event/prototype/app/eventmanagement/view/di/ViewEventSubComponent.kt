package event.prototype.app.eventmanagement.view.di

import dagger.Subcomponent
import event.prototype.app.eventmanagement.view.ViewEventActivity


@Subcomponent(modules = [ViewEventModule::class])
interface ViewEventSubComponent {

     fun inject(viewEventActivity: ViewEventActivity)
}