package com.racjonalnytraktor.findme3.data.network

import com.racjonalnytraktor.findme3.data.model.UpdateTokenRequest
import com.racjonalnytraktor.findme3.data.network.model.*
import com.racjonalnytraktor.findme3.data.network.model.createping.CreatePingRequest
import com.racjonalnytraktor.findme3.data.network.model.login.LoginRequest
import com.racjonalnytraktor.findme3.data.network.model.login.LoginResponse
import com.racjonalnytraktor.findme3.data.network.model.register.RegisterFbRequest
import com.racjonalnytraktor.findme3.data.network.model.register.RegisterFbResponse
import com.racjonalnytraktor.findme3.data.network.model.register.RegisterRequest
import com.racjonalnytraktor.findme3.data.network.model.register.RegisterResponse
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

    @POST("group/acceptInvitation")
    fun acceptInvitation(@Header("X-Token") token: String, @Body request: AcceptInvitationRequest)
    : Single<String>

    @GET("group/subgroups/{groupId}")
    fun getSubGroups(@Header("X-Token") token: String)

    @GET("group/allSubGroups/{groupId}")
    fun getAllSubGroups(@Header("X-Token") token: String,@Path("groupId") groupId: String):
            Observable<List<String>>

    @POST("notif/updateToken")
    fun updateNotifToken(@Header("X-Token")token: String, @Body notifToken: UpdateTokenRequest): Single<String>

    @GET("user/invitations")
    fun getInvitations(@Header("X-Token")token: String): Observable<InvitationResponse>

    @GET("user/groupList")
    fun getGroups(@Header("X-Token")token: String): Observable<GroupsResponse>

    @POST("ping/create")
    fun createPing(@Header("X-Token")token: String, @Body request: CreatePingRequest): Single<String>
}