package  event.prototype.app.eventmanagement.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import event.prototype.app.eventmanagement.addevent.viewmodel.AddEventViewModel
import event.prototype.app.eventmanagement.alarm.viewmodel.AlarmViewModel
import event.prototype.app.eventmanagement.eventtypeselection.viewmodel.EventTypeViewModel
import event.prototype.app.eventmanagement.home.viewmodel.HomeViewModel
import event.prototype.app.eventmanagement.update.viewmodel.UpdateEventViewModel
import event.prototype.app.eventmanagement.view.viewmodel.ViewEventViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun HomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventTypeViewModel::class)
    internal abstract fun EventTypeViewModel(viewModel: EventTypeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddEventViewModel::class)
    internal abstract fun AddEventViewModel(viewModel: AddEventViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewEventViewModel::class)
    internal abstract fun ViewEventViewModel(viewModel: ViewEventViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateEventViewModel::class)
    internal abstract fun UpdateEventViewModel(viewModel: UpdateEventViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlarmViewModel::class)
    internal abstract fun AlarmViewModel(viewModel: AlarmViewModel): ViewModel
}