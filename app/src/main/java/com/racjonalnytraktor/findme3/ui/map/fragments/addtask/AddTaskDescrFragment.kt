package com.racjonalnytraktor.findme3.ui.map.fragments.addtask

import android.app.Activity
import android.app.TimePickerDialog
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.racjonalnytraktor.findme3.R
import com.racjonalnytraktor.findme3.data.model.new.CreateActionRequest
import com.racjonalnytraktor.findme3.ui.base.BaseFragment
import com.racjonalnytraktor.findme3.ui.map.MapMvp
import com.racjonalnytraktor.findme3.ui.map.fragments.pickers.TimePickerFragment
import com.racjonalnytraktor.findme3.ui.map.listeners.Listener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.add_task_description.*
import java.util.*

class AddTaskDescrFragment<V: MapMvp.View>: BaseFragment<V>(), TimePickerDialog.OnTimeSetListener
,Listener.ChangeLocation{

    lateinit var parentListener: DescriptionListener

    var date = Date()

    var mLocation: Location = Location("")
    var mHourText = "-1"
    var mMinuteText = "-1"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_task_description,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fieldTitle.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0 != null)
                    parentListener.onTitleChanged(p0.toString())
            }

        })

        buttonShowDate.setOnClickListener {
            showClockDialog()
        }

        buttonChangeLocation.setOnClickListener {
            parentMvp.getPresenter().onChangeLocationClick(this)
        }

        buttonAddUsers.setOnClickListener {
            parentListener.onAddUsersClick()
        }

        switchAction.setCheckedPosition(0)
        switchAction.setOnClickListener {
            Toasty.normal(parentContext,switchAction.checkedPosition.toString()).show()
            Log.d("switchAction","pos: $switchAction.checkedPosition")
        }

        if(mLocation.latitude != 0.0)
            textLocation?.text = "${mLocation.latitude.toFloat()}, ${mLocation.longitude.toFloat()}"
        if (mHourText != "-1")
            textDate.text = "$mHourText:$mMinuteText"

    }

    private fun showClockDialog() {
        val viewDate = layoutInflater.inflate(R.layout.dialog_time,null)
        //val timePicker = TimePicker(parentMvp.getCtx())
        val timePicker = TimePickerFragment()
        timePicker.onTimeSetListener = this
        timePicker.show((parentContext as Activity).fragmentManager,"")

    }

    fun getActionData(): CreateActionRequest{
        val action = CreateActionRequest()
        action.title = fieldTitle?.text.toString()
        action.desc = fieldDescr?.text.toString()
        action.type = if(switchAction.checkedPosition == 0) "ping" else "info"

        try {
            val format = java.text.SimpleDateFormat("EEE MMM dd YYYY HH:mm:ss z",Locale.ENGLISH)
            action.geo[0] = mLocation.latitude
            action.geo[1] = mLocation.longitude
            val calendar = Calendar.getInstance()
            if(mMinuteText != "-1"){
                calendar.set(Calendar.MINUTE,mMinuteText.toInt())
                calendar.set(Calendar.HOUR_OF_DAY,mHourText.toInt())
                Log.d("calendar time",calendar.time.toString())
                date.time = calendar.timeInMillis
                action.plannedTime = format.format(date)

            }
        }catch (e: Exception){ }



        return action
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        date.time = (hourOfDay * 3600 + minute * 60).toLong()
        mHourText = if(hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
        mMinuteText = if(minute < 10) "0$minute" else "$minute"
        textDate.text = "$mHourText:$mMinuteText"
        parentListener.onDateChanged("Termin powiadomienia: $mHourText:$mMinuteText")
    }

    override fun changeLocation(location: Location){
        mLocation.set(location)
        textLocation?.text = "${location.latitude.toFloat()}, ${location.longitude.toFloat()}"
    }

}