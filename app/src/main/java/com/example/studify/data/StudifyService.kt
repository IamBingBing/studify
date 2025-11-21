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
    fun requestchat(@FieldMap data: Map<String, String>): Single<ChatModel>
    @FormUrlEncoded
    @POST("api/group.php")
    fun requestgroup (@FieldMap data: Map<String, String>): Single<GroupModel>
    @FormUrlEncoded
    @POST("api/userdata.php")
    fun requestUserData(@FieldMap data: Map<String, String>): Single<LoginModel>
    @FormUrlEncoded
    @POST("api/shopdata.php")
    fun requestShopData (@FieldMap data : Map<String,String>): Single<ShopModel>
    @FormUrlEncoded
    @POST("api/matchinguser.php")
    fun requestMatchingUser(@FieldMap data : Map<String, String>): String
    @FormUrlEncoded
    @POST("api/mentorqnadata.php")
    fun requestgroupMentorQnaData(@FieldMap data: Map<String, String>) : Single<List<QnaModel>>
    @FormUrlEncoded
    @POST("api/groupdata.php")
    fun requestGroupData(@FieldMap data : Map<String, String>) : Single<GroupModel>
    @FormUrlEncoded
    @POST("api/noticedata.php")
    fun requestNoticeData(@FieldMap data: Map<String, String>): Single<List<AnnounceModel>>
    @FormUrlEncoded
    @POST("api/datedata.php")
    fun requestDateData(@FieldMap data : Map<String, String>): Single<List<DateModel>>
    @FormUrlEncoded
    @POST("Mentordata.php")
    fun requestMentorData(@FieldMap data: Map<String, String>): Single<MentorModel>
    @FormUrlEncoded
    @POST( "addProgress.php")
    fun UpdateProgress(@FieldMap data: Map<String, String>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("addDate.php")
    fun UpdateDate (@FieldMap data: Map<String, String>): Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/registerUser.php")
    fun UpdateUser (@FieldMap data : Map<String, String>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/addQna.php")
    fun UpdateQna (@FieldMap data : Map<String, String>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/addGroup.php")
    fun UpdateGroup(@FieldMap data : Map<String, String>) : Single<UpdateModel>

}