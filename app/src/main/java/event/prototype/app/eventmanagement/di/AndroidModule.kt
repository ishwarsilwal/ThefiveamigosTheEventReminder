package event.prototype.app.eventmanagement.di

import android.content.Context
import dagger.Module
import dagger.Provides
import event.prototype.app.eventmanagement.MasterApplication
import javax.inject.Singleton


@Module
public class AndroidModule(private var application: MasterApplication) {

    @Provides
    @Singleton
    internal fun providesMasterApplication(): MasterApplication {
        return application
    }

    @Provides
    @Singleton
    internal fun providesApplicationContext(): Context {
        return application.applicationContext
    }

}