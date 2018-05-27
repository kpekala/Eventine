package com.racjonalnytraktor.findme3.data.repository

import android.util.Log
import com.racjonalnytraktor.findme3.data.model.User
import com.racjonalnytraktor.findme3.data.network.model.LoginRequest
import com.racjonalnytraktor.findme3.data.network.model.LoginResponse
import com.racjonalnytraktor.findme3.data.network.model.RegisterFbRequest
import com.racjonalnytraktor.findme3.data.network.model.RegisterFbResponse
import com.racjonalnytraktor.findme3.utils.SchedulerProvider
import com.racjonalnytraktor.findme3.utils.WhereIsJson
import io.reactivex.Observable
import io.reactivex.Single

class LoginRepository: BaseRepository() {

    fun loginWithEmail(loginRequest: LoginRequest): Observable<LoginResponse>{
        return rest.networkService.login(loginRequest)
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
    }

    fun updateUserWhenSignIn(userId: String){

    }

    fun getUserInfo(): Single<User>{
        return mFacebook.getUserBasicInfo()
                .map { t -> WhereIsJson.getUserBasic(t.jsonObject) }
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())

    }

    fun setCurrentUser(user: User){
        user.facebookId = mFacebook.getAccessToken().userId
        prefs.setCurrentUser(user)
    }

    fun registerByFacebook(user: User): Single<RegisterFbResponse>{
        val request = RegisterFbRequest(user.facebookId,user.fullName)
        Log.d("requestid",request.facebookId)
        Log.d("requestname",request.fullName)

        return rest.networkService.registerByFacebook(request)
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
    }
}