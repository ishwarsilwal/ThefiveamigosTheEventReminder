package event.prototype.app.eventmanagement.utils

import android.content.res.ColorStateList
import android.databinding.BindingAdapter
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.widget.ImageView
import event.prototype.app.eventmanagement.R
import event.prototype.app.eventmanagement.model.phone.PhoneContactModel
import kotlinx.android.synthetic.main.activity_add_event.*

@BindingAdapter("android:setDrawableImage")
fun ImageView.setDrawableImage(src : Int){
    this.setImageResource(src)
}

