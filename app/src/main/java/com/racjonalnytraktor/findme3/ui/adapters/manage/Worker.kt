package com.racjonalnytraktor.findme3.ui.adapters.manage

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class Worker() : Parcelable {

    lateinit var name: String
    lateinit var id: String
    var selected = false

    @SuppressLint("ParcelClassLoader")
    constructor(parcel: Parcel) : this() {
        val bundle = parcel.readBundle()
        name = bundle.getString("name")
        id = bundle.getString("id")
        selected = bundle.getBoolean("selected",false)
    }

    constructor(name: String, id: String, selected: Boolean = false) : this() {
        this.name = name
        this.id = id
        this.selected = selected
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        val bundle = Bundle()
        bundle.putString("name",name)
        bundle.putString("id",id)
        bundle.putBoolean("selected",selected)
        parcel.writeBundle(bundle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Worker> {
        override fun createFromParcel(parcel: Parcel): Worker {
            return Worker(parcel)
        }

        override fun newArray(size: Int): Array<Worker?> {
            return arrayOfNulls(size)
        }
    }
}