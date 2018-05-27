package com.racjonalnytraktor.findme3.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.racjonalnytraktor.findme3.utils.DeviceInfo
import com.racjonalnytraktor.findme3.utils.PermissionsHelper
import io.reactivex.Completable
import io.reactivex.Observable
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


open class BaseActivity : AppCompatActivity(),MvpView{

    private val REQUEST_CHECK_SETTINGS = 1905

    protected lateinit var mProgress: ProgressBar
    private lateinit var mDeviceInfo: DeviceInfo
    private lateinit var mPermissionsHelper: PermissionsHelper

    private var mPermissionStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDeviceInfo = DeviceInfo(this)
        mPermissionsHelper = PermissionsHelper(this)
    }

    override fun showLoading() {
        if(::mProgress.isInitialized){
            hideLoading()
            mProgress.isIndeterminate = true
            mProgress.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        if(::mProgress.isInitialized){
            mProgress.isIndeterminate = false
            mProgress.visibility = View.INVISIBLE
        }
    }

    override fun hideKeyboard() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: Int) {
        toast(message)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun isConnectedToNetwork(): Boolean {
        return mDeviceInfo.isConnected()
    }

    override fun checkPermission(permission: String): Observable<PermissionsHelper.PermissionState> {
        return mPermissionsHelper.checkPermission(permission)
    }

    override fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun increaseAlpha() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decreaseAlpha() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkLocationSettings(): Completable {

       return Completable.create {emitter ->
            val builder = LocationSettingsRequest.Builder()
                    .addLocationRequest(LocationRequest().apply {
                        interval = 1000
                        fastestInterval = 1000
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    })


            val settingsClient = LocationServices.getSettingsClient(this)
            val taskCheckSettings = settingsClient.checkLocationSettings(builder.build())


            taskCheckSettings.addOnSuccessListener{
                Log.d("Michno", "OnSuccessListener")
                emitter.onComplete()
            }

            taskCheckSettings.addOnFailureListener { exception ->
                if (exception is ResolvableApiException){
                    Log.d("Michno","OnFailureListener")
                    try {
                        exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                        doAsync {
                            while (!mPermissionStatus){
                                Thread.sleep(100)
                            }
                            uiThread {
                                emitter.onComplete()
                            }
                        }
                    } catch (sendEx: IntentSender.SendIntentException) {
                    }
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CHECK_SETTINGS){
            mPermissionStatus = true
        }
    }

    override fun getCtx(): Context {
        return this
    }
}