package event.prototype.app.eventmanagement.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import event.prototype.app.eventmanagement.data.MainDataBase
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): MainDataBase =
            Room.databaseBuilder(context, MainDataBase::class.java, "event-db").build()

    @Singleton
    @Provides
    fun providesEventDao(database: MainDataBase) = database.eventDao()

    @Singleton
    @Provides
    fun providesPhoneDao(database: MainDataBase) = database.phoneDao()

    @Singleton
    @Provides
    fun providesEmail(database: MainDataBase) = database.emailDao()

}
