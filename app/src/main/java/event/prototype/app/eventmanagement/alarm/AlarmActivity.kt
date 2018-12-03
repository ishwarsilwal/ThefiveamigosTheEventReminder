package event.prototype.app.eventmanagement.alarm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.telephony.SmsManager
import android.view.WindowManager
import android.widget.Toast
import event.prototype.app.eventmanagement.BaseActivity
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.alarm.viewmodel.AlarmViewModel
import event.prototype.app.eventmanagement.databinding.ActivityAlarmBinding
import event.prototype.app.eventmanagement.di.MainComponent
import event.prototype.app.eventmanagement.utils.AlarmSoundPlayer
import event.prototype.app.eventmanagement.utils.Constants
import kotlinx.android.synthetic.main.activity_alarm.*
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import event.prototype.app.eventmanagement.utils.runOnIoThread


class AlarmActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var alarmViewModel: AlarmViewModel

    private lateinit var activityAlarmBinding: ActivityAlarmBinding

    override fun configureDependencies(mainComponent: MainComponent) {
        mainComponent.alarmSubComponent().inject(this)
    }

    var alarmSoundPlayer: AlarmSoundPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    +WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    +WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )

        activityAlarmBinding = DataBindingUtil.setContentView(this, R.layout.activity_alarm)

        alarmViewModel = ViewModelProviders.of(this, viewModelFactory)[AlarmViewModel::class.java]

        activityAlarmBinding.apply {
            setLifecycleOwner(this@AlarmActivity)
            alarmViewModel = this@AlarmActivity.alarmViewModel
        }

        val eventId = intent.getStringExtra(Constants.EVENTID)

        alarmViewModel.getParticularEvent(eventId).observe(this, Observer { eventAllDetails ->
            alarmViewModel.eventMutableData.value = eventAllDetails

            if (eventAllDetails != null) {
                send.visibility = View.VISIBLE
            }

            send.setOnClickListener {

                runOnIoThread {
                    alarmViewModel.updateEventAsCompleted(eventAllDetails?.event!!)
                }

                Log.d("AlarmActivity", eventAllDetails?.phoneContactList?.size.toString() + " Phone Number")
                Log.d("AlarmActivity", eventAllDetails?.emailContactList?.size.toString() + " Email")

                eventAllDetails?.phoneContactList?.forEach {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(it.phoneNumber, null, eventAllDetails.event?.eventWish, null, null)
                    Log.d("AlarmActivity",it.phoneNumber + " SMS sent")
                }

                val emailArray = arrayOfNulls<String>(eventAllDetails?.emailContactList?.size!!)
                for (i in 0 until eventAllDetails.emailContactList.size) {
                    emailArray[i] = eventAllDetails.emailContactList[i].emailAddress
                }

                val intent = Intent(Intent.ACTION_SENDTO)
                    .setData(Uri.Builder().scheme("mailto").build())
                    .putExtra(Intent.EXTRA_EMAIL, emailArray)
                    .putExtra(Intent.EXTRA_SUBJECT, eventAllDetails.event?.eventType?.name)
                    .putExtra(Intent.EXTRA_TEXT, eventAllDetails.event?.eventWish)
                try {
                    startActivity(intent)
                    finish()
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(this@AlarmActivity, "There are no email clients installed.", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        })

    }

    override fun onResume() {
        super.onResume()
        startPlayingTrack()
    }

    override fun onPause() {
        alarmSoundPlayer?.pausePlayingSound()
        super.onPause()
    }

    override fun onDestroy() {
        alarmSoundPlayer?.stopPlayingSound()
        super.onDestroy()
    }

    private fun startPlayingTrack() {
        if (alarmSoundPlayer == null)
            alarmSoundPlayer = AlarmSoundPlayer(R.raw.time_clock_alarm, this)

        alarmSoundPlayer!!.startPlayingSound()
    }
}
