package com.example.studify.data

import com.example.studify.data.model.AnnounceModel
import com.example.studify.data.model.LoginModel
import com.example.studify.data.model.ChatModel
import com.example.studify.data.model.DateModel
import com.example.studify.data.model.GroupModel
import com.example.studify.data.model.MentorModel
import com.example.studify.data.model.QnaModel
import com.example.studify.data.model.ShopModel
import com.example.studify.data.model.UpdateModel
import com.example.studify.data.req.GroupReqModel
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StudifyService {
    @FormUrlEncoded
    @POST("api/login.php")
    fun requestLogin(@FieldMap data: Map<String, String>): Single<LoginModel>
    @FormUrlEncoded
    @POST("api/chat.php")
    fun requestchat(@FieldMap data: Map<String, Int>): Single<ChatModel>
    @FormUrlEncoded
    @POST("api/group.php")
    fun requestgroup (@FieldMap data: Map<String, Int>): Single<GroupModel>
    @FormUrlEncoded
    @POST("api/announce.php")
    fun requestAnnounce (@FieldMap data :Map<String,Int>): Single<AnnounceModel>
    @FormUrlEncoded
    @POST("api/userdata.php")
    fun requestUserData(@FieldMap data: Map<String, String>): Single<LoginModel>
    @FormUrlEncoded
    @POST("api/shopdata.php")
    fun requestShopData (@FieldMap data : Map<String,Any>): Single<ShopModel>
    @FormUrlEncoded
    @POST("api/matchinguser.php")
    fun requestMatchingUser(@FieldMap data : Map<String, Any>): String
    @FormUrlEncoded
    @POST("api/groupqnadata.php")
    fun requestgroupQnaData(@FieldMap data: Map<String, Any>) : Single<QnaModel>
    @FormUrlEncoded
    @POST("api/mentorqnadata.php")
    fun requestgroupMentorQnaData(@FieldMap data: Map<String, Any>) : Single<QnaModel>
    @FormUrlEncoded
    @POST("api/groupdata.php")
    fun requestGroupData(@FieldMap data : Map<String, Any>) : Single<GroupModel>
    @FormUrlEncoded
    @POST("api/noticedata.php")
    fun requestNoticeData(@FieldMap data: Map<String, Any>): Single<AnnounceModel>
    @FormUrlEncoded
    @POST("api/datedata.php")
    fun requestDateData(@FieldMap data : Map<String, Any>): Single<DateModel>
    @FormUrlEncoded
    @POST("Mentordata.php")
    fun requestMentorData(@FieldMap data: Map<String, Any>): Single<MentorModel>

    @FormUrlEncoded
    @POST( "addProgress.php")
    fun UpdateProgress(@FieldMap data: Map<String, Any>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("addDate.php")
    fun UpdateDate (@FieldMap data: Map<String, Any>): Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/registerUser.php")
    fun UpdateUser (@FieldMap data : Map<String, Any>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/addQna.php")
    fun UpdateQna (@FieldMap data : Map<String, Any>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/addGroup.php")
    fun UpdateGroup(@FieldMap data : Map<String, Any>) : Single<UpdateModel>

}