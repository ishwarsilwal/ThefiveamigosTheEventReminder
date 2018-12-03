package event.prototype.app.eventmanagement.addevent

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.chip.Chip
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import event.prototype.app.eventmanagement.BaseActivity
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.addevent.viewmodel.AddEventViewModel
import event.prototype.app.eventmanagement.databinding.ActivityAddEventBinding
import event.prototype.app.eventmanagement.di.MainComponent
import event.prototype.app.eventmanagement.model.email.EmailContactModel
import event.prototype.app.eventmanagement.model.event.EventAllDetails
import event.prototype.app.eventmanagement.model.event.EventType
import event.prototype.app.eventmanagement.model.phone.PhoneContactModel
import event.prototype.app.eventmanagement.utils.Constants
import event.prototype.app.eventmanagement.utils.ContactListAdapter
import event.prototype.app.eventmanagement.utils.EventObserver
import kotlinx.android.synthetic.main.activity_add_event.*
import java.util.*
import javax.inject.Inject


class AddEventActivity : BaseActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var eventAllDetails: EventAllDetails

    companion object {
        val PROJECTION: Array<out String> = arrayOf(
            ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Data.PHOTO_THUMBNAIL_URI
        )
    }

    private lateinit var addEventViewModel: AddEventViewModel

    private lateinit var activityAddEventBinding: ActivityAddEventBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun configureDependencies(mainComponent: MainComponent) {
        mainComponent.addEventSubComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val formType = intent.getStringExtra(Constants.FORMTYPE)

        var eventType: EventType? = null

        if (formType == Constants.ADD) {
            eventType = intent.getSerializableExtra(Constants.EVENTTYPE) as EventType
        } else {
            eventAllDetails = intent.getSerializableExtra(Constants.EVENTALLDETAILS) as EventAllDetails
            eventType = eventAllDetails.event?.eventType
        }

        addEventViewModel = ViewModelProviders.of(this, viewModelFactory)[AddEventViewModel::class.java]

        activityAddEventBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_event)
        activityAddEventBinding.apply {
            appBarLayout?.toolBar.apply {
                if (formType == Constants.ADD)
                    this?.title =
                            if (eventType == EventType.BIRTHDAY) getString(R.string.add_birthday) else if (eventType == EventType.ANNIVERSARY) getString(
                                R.string.add_anniversary
                            ) else getString(R.string.add_custom)
                else
                    this?.title =
                            if (eventType == EventType.BIRTHDAY) getString(R.string.update_birthday) else if (eventType == EventType.ANNIVERSARY) getString(
                                R.string.update_anniversary
                            ) else getString(R.string.update_custom)
                this?.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
            addEventViewModel = this@AddEventActivity.addEventViewModel
        }

        addEventViewModel.eventType = eventType!!


        if (formType == Constants.UPDATE) {

            addEventViewModel.update = true
            saveOrUpdateBtn.text = "Update"

            addEventViewModel.eventWish = eventAllDetails.event?.eventWish!!
            addEventViewModel.eventTime = eventAllDetails.event?.eventTime!!
            addEventViewModel.eventDate = eventAllDetails.event?.eventDate!!
            addEventViewModel.eventAllDetails = eventAllDetails

            eventAllDetails.phoneContactList.forEach { phoneContactModel ->
                val chip = Chip(this)
                chip.text = phoneContactModel.phoneNumber
                chip.tag = phoneContactModel.phoneNumber
                chip.isCloseIconVisible = true
                chip.chipBackgroundColor = ColorStateList.valueOf(resources.getColor(R.color.material_grey_100))

                chipGroup.addView(chip)

                phoneContactModel.isAdded = true

                addEventViewModel.phoneContactModel = phoneContactModel

                chip.setOnCloseIconClickListener {
                    phoneContactModel.isAdded = false
                    addEventViewModel.phoneContactModel = phoneContactModel
                    chipGroup.removeView(it)
                }
            }

            eventAllDetails.emailContactList.forEach { emailContactModel ->
                val chip = Chip(this)
                chip.text = emailContactModel.emailAddress
                chip.isCloseIconVisible = true
                chip.chipBackgroundColor = ColorStateList.valueOf(resources.getColor(R.color.material_grey_100))

                emailChipGroup.addView(chip)

                emailContactModel.isAdded = true
                addEventViewModel.emailModel = emailContactModel

                chip.setOnCloseIconClickListener {
                    emailContactModel.isAdded = false
                    addEventViewModel.emailModel = emailContactModel
                    emailChipGroup.removeView(it)
                }
            }
        }

        if (eventType == EventType.BIRTHDAY) addEventViewModel.eventWish = "Happy Birthday" else if (eventType == EventType.ANNIVERSARY) addEventViewModel.eventWish ="Happy Anniversary" else addEventViewModel.eventWish =""

        val act = findViewById<View>(R.id.autocompleteText) as AutoCompleteTextView
        val content = contentResolver
        val cursor = content.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null)
        val adapter = ContactListAdapter(this, cursor, true)
        act.threshold = 3
        act.setAdapter(adapter)

        act.setOnItemClickListener { adapterView, view, position, l ->
            act.dismissDropDown()
        }

//        act.setOnItemClickListener { adapterView, view, position, l ->
//            addEventViewModel.eventPhone = ""
//            act.setText("")
//            val userName = (adapterView.getItemAtPosition(position) as Cursor).getString(1)
//            val phoneNumber = (adapterView.getItemAtPosition(position) as Cursor).getString(2)
//
//            val chip = Chip(this)
//            chip.text = userName
//            chip.tag = phoneNumber
//            chip.isCloseIconVisible = true
//            chip.chipBackgroundColor = ColorStateList.valueOf(resources.getColor(R.color.material_grey_100))
//
//            chipGroup.addView(chip)
//
//            addEventViewModel.phoneContactModel = PhoneContactModel(
//                name = userName,
//                phoneNumber = phoneNumber,
//                isAdded = true
//            )
//
//            chip.setOnCloseIconClickListener {
//                addEventViewModel.phoneContactModel = PhoneContactModel(
//                    name = userName,
//                    phoneNumber = phoneNumber,
//                    isAdded = false
//                )
//                chipGroup.removeView(it)
//            }
//        }

        addEventViewModel.openDatePickerObserver.observe(this, EventObserver {
            openDatePicker()
        })

        addEventViewModel.openTimePickerObserver.observe(this, EventObserver {
            openTimePicker()
        })

        addEventViewModel.saveCompleteObserver.observe(this, EventObserver {
            if(it) {
                Toast.makeText(this,"Saved and scheduled successfully", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }else{
                Toast.makeText(this,"Cannot schedule event. Alarm Time must be greater than current time.", Toast.LENGTH_SHORT).show()
            }
        })

        addEventViewModel.newEmailListObserver.observe(this, Observer { emailContactModel ->
            Toast.makeText(this, emailContactModel?.emailAddress + " Added" , Toast.LENGTH_SHORT)
                .show()
            if (emailContactModel?.isAdded!!) {
                val chip = Chip(this)
                chip.text = emailContactModel.emailAddress
                chip.isCloseIconVisible = true
                chip.chipBackgroundColor = ColorStateList.valueOf(resources.getColor(R.color.material_grey_100))

                emailChipGroup.addView(chip)

                addEventViewModel.emailModel = EmailContactModel(
                    emailAddress = emailContactModel.emailAddress,
                    isAdded = true
                )

                chip.setOnCloseIconClickListener {
                    addEventViewModel.emailModel = EmailContactModel(
                        emailAddress = emailContactModel.emailAddress,
                        isAdded = false
                    )
                    emailChipGroup.removeView(it)
                }
            }
        })

        addEventViewModel.newPhoneListObserver.observe(this, Observer { phoneContactModel ->
            Toast.makeText(this, phoneContactModel?.phoneNumber + " Added", Toast.LENGTH_SHORT).show()
            if (phoneContactModel?.isAdded!!) {
                val chip = Chip(this)
                chip.text = phoneContactModel.phoneNumber
                chip.isCloseIconVisible = true
                chip.chipBackgroundColor = ColorStateList.valueOf(resources.getColor(R.color.material_grey_100))

                chipGroup.addView(chip)

                addEventViewModel.phoneContactModel = PhoneContactModel(
                    phoneNumber = phoneContactModel.phoneNumber,
                    isAdded = true
                )

                chip.setOnCloseIconClickListener {
                    addEventViewModel.phoneContactModel = PhoneContactModel(
                        phoneNumber = phoneContactModel.phoneNumber,
                        isAdded = false
                    )
                    chipGroup.removeView(it)
                }
            }
        })
    }


    private fun openDatePicker() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
            this@AddEventActivity,
            now.get(Calendar.YEAR), // Initial year selection
            now.get(Calendar.MONTH), // Initial month selection
            now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        )

        dpd.isThemeDark = true

        dpd.setOkColor(resources.getColor(R.color.mdtp_white))
        dpd.setCancelColor(resources.getColor(R.color.mdtp_white))

        // If you're calling this from a support Fragment
        dpd.version = DatePickerDialog.Version.VERSION_1
        dpd.show(fragmentManager, "Datepickerdialog")
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val month = monthOfYear + 1

        var date = "$year-"

        date += if (month <= 9) {
            "0$month-"
        } else {
            "$month-"
        }

        date += if (dayOfMonth <= 9) {
            "0$dayOfMonth"
        } else {
            "$dayOfMonth"
        }

        addEventViewModel.eventDate = date
    }

    private fun openTimePicker() {
        val now = Calendar.getInstance()
        val dpd = TimePickerDialog.newInstance(
            this@AddEventActivity,
            now.get(Calendar.HOUR_OF_DAY), // Initial year selection
            now.get(Calendar.MINUTE), // Initial month selection
            true // Inital day selection
        )
        // If you're calling this from a support Fragment
        dpd.version = TimePickerDialog.Version.VERSION_2
        dpd.show(fragmentManager, "Datepickerdialog")
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        var time = ""

        time += if (hourOfDay <= 9) {
            "0$hourOfDay:"
        } else {
            "$hourOfDay:"
        }

        time += if (minute <= 9) {
            "0$minute:"
        } else {
            "$minute:"
        }

        time += if (second <= 9) {
            "0$second"
        } else {
            "$second"
        }

        addEventViewModel.eventTime = time
    }
}
