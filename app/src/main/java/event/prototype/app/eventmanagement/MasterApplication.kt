package event.prototype.app.eventmanagement

import android.app.Application
import com.facebook.stetho.Stetho
import com.evernote.android.job.JobManager
import event.prototype.app.eventmanagement.di.AndroidModule
import event.prototype.app.eventmanagement.di.DaggerMainComponent
import event.prototype.app.eventmanagement.di.MainComponent
import event.prototype.app.eventmanagement.job.EventJobCreator
import net.danlew.android.joda.JodaTimeAndroid


class MasterApplication : Application() {

    private lateinit var mMainComponent: MainComponent

    companion object {
        private lateinit var masterApplication: MasterApplication
        fun getMasterApplication() = masterApplication
    }

    override fun onCreate() {
        super.onCreate()

        masterApplication = this

        JobManager.create(this).addJobCreator(EventJobCreator())
        JodaTimeAndroid.init(this)


        mMainComponent = DaggerMainComponent.builder()
            .androidModule(AndroidModule(this))
            .build()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    fun getMainComponent(): MainComponent {
        return mMainComponent
    }

}