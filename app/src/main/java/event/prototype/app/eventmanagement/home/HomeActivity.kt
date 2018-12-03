package event.prototype.app.eventmanagement.home

import android.Manifest
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import event.prototype.app.eventmanagement.BaseActivity
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.databinding.ActivityHomeBinding
import event.prototype.app.eventmanagement.di.MainComponent
import event.prototype.app.eventmanagement.eventtypeselection.EventTypeActivity
import event.prototype.app.eventmanagement.home.viewmodel.HomeViewModel
import event.prototype.app.eventmanagement.job.EventJob
import event.prototype.app.eventmanagement.utils.EventObserver
import event.prototype.app.eventmanagement.view.ViewEventActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


class HomeActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {

    companion object {
        const val RC_SMS_AND_CONTACT = 1001
    }

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var activityHomeBinding: ActivityHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var mLastJobId: Int = 0

    override fun configureDependencies(mainComponent: MainComponent) {
        mainComponent.homeSubComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        activityHomeBinding.homeViewModel = homeViewModel

        homeViewModel.navigateToAddObserver.observe(this, EventObserver {
            methodRequireSMSAndContactPermission(Intent(this, EventTypeActivity::class.java))
        })

        homeViewModel.navigateToUpdateObserver.observe(this, EventObserver {
            Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()
        })

        homeViewModel.navigateToDeleteObserver.observe(this, EventObserver {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
        })

        homeViewModel.navigateToViewObserver.observe(this, EventObserver {
            methodRequireSMSAndContactPermission(Intent(this, ViewEventActivity::class.java))
        })

//        val phoneList: MutableList<PhoneContactModel> = mutableListOf()
//        phoneList.add(PhoneContactModel(phoneNumber = "9842248633"))
//        phoneList.add(PhoneContactModel(phoneNumber = "9851184633"))

//        phoneList.forEach {
//            val smsManager = SmsManager.getDefault()
//            smsManager.sendTextMessage(it.phoneNumber, null, "Whshing", null, null)
//        }


//        val emailList: MutableList<EmailContactModel> = mutableListOf()
//        emailList.add(EmailContactModel(emailAddress = "taman.neupane@gmail.com"))
//        emailList.add(EmailContactModel(emailAddress = "taman.neupane@peacenepal.com"))
//
//
//        val emailArray = arrayOfNulls<String>(emailList.size)
//        for (i in 0 until emailList.size) {
//            emailArray[i] = emailList[i].emailAddress
//        }
//
//        val intent = Intent(Intent.ACTION_SENDTO)
//            .setData(Uri.Builder().scheme("mailto").build())
//            .putExtra(Intent.EXTRA_EMAIL, emailArray)
//            .putExtra(Intent.EXTRA_SUBJECT, "Email Subject")
//            .putExtra(Intent.EXTRA_TEXT, "Email Message")
//        try {
//            startActivity(Intent.createChooser(intent, "Send Event mail..."))
//        } catch (ex: android.content.ActivityNotFoundException) {
//            Toast.makeText(this@HomeActivity, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
//        }
    }

    @AfterPermissionGranted(RC_SMS_AND_CONTACT)
    private fun methodRequireSMSAndContactPermission(intent: Intent) {
        val perms = arrayOf<String>(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS)
        if (hasSmsAndContactsPermissions()) {
            startActivity(intent)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, getString(R.string.sms_and_contact_permission),
                RC_SMS_AND_CONTACT, *perms
            )
        }
    }

    override
    fun onPermissionsGranted(requestCode: Int, list: List<String>) {

    }

    override
    fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        // Some permissions have been denied
        // ...
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    private val SMS_AND_CONTACTS =
        arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS)

    private fun hasSmsAndContactsPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, *SMS_AND_CONTACTS)
    }


    private fun testExact() {
        val extras = PersistableBundleCompat()
        extras.putString("key", "Hello world")

        mLastJobId = JobRequest.Builder(EventJob.TAG)
            .setBackoffCriteria(5_000L, JobRequest.BackoffPolicy.EXPONENTIAL)
            .setExtras(extras)
            .setUpdateCurrent(true)
            .setExact(90_000L)
            .build()
            .schedule()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
