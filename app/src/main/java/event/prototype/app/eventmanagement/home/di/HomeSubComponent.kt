package event.prototype.app.eventmanagement.home.di

import dagger.Subcomponent
import event.prototype.app.eventmanagement.home.HomeActivity


@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {

     fun inject(homeActivity: HomeActivity)
}