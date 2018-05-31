package com.racjonalnytraktor.findme3.data.network

import com.racjonalnytraktor.findme3.data.model.UpdateTokenRequest
import com.racjonalnytraktor.findme3.data.network.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface Routes {

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>

    @POST("auth/social")
    fun registerByFacebook(@Body registerRequest: RegisterFbRequest): Single<RegisterFbResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Single<RegisterResponse>

    @POST("group/create")
    fun createGroup(@Header("X-Token") token: String, @Body request: CreateGroupRequest): Single<String>

    @POST("group/join")
    fun joinGroup(@Header("X-Token") token: String, @Body request: JoinRequest): Single<String>

    @POST("notif/updateToken")
    fun updateNotifToken(@Header("X-Token")token: String, @Body notifToken: UpdateTokenRequest): Single<String>

    @GET("user/invitations")
    fun getInvitations(@Header("X-Token")token: String): Observable<InvitationResponse>

}