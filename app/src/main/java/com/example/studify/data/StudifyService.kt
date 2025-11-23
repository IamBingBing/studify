package com.example.studify.data

import com.example.studify.data.model.AnnounceModel
import com.example.studify.data.model.LoginModel
import com.example.studify.data.model.ChatModel
import com.example.studify.data.model.DateModel
import com.example.studify.data.model.GroupModel
import com.example.studify.data.model.MentorModel
import com.example.studify.data.model.ProgressModel
import com.example.studify.data.model.QnaModel
import com.example.studify.data.model.RegisterModel
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
    @POST("api/userdata.php")
    fun requestUserData(@FieldMap data: Map<String, String>): Single<LoginModel>
    @FormUrlEncoded
    @POST("api/updateUser.php")
    fun requestUpdateUser(@FieldMap data: Map<String, String>): Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/shopdata.php")
    fun requestShopData (@FieldMap data : Map<String,String>): Single<ShopModel>
    @FormUrlEncoded
    @POST("api/mentorqnadata.php")
    fun requestgroupMentorQnaData(@FieldMap data: Map<String, String>) : Single<QnaModel>
    @FormUrlEncoded
    @POST("api/groupdata.php")
    fun requestGroupData(@FieldMap data : Map<String, String>) : Single<GroupModel>
    @FormUrlEncoded
    @POST("api/noticedata.php")
    fun requestNoticeData(@FieldMap data: Map<String, String>): Single<AnnounceModel>
    @FormUrlEncoded
    @POST("api/datedata.php")
    fun requestDateData(@FieldMap data : Map<String, String>): Single<DateModel>
    @FormUrlEncoded
    @POST("Mentordata.php")
    fun requestMentorData(@FieldMap data: Map<String, String>): Single<MentorModel>
    @FormUrlEncoded
    @POST( "addProgress.php")
    fun UpdateProgress(@FieldMap data: Map<String, String>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/getProgress.php")
    fun requestGetProgress(@FieldMap data: Map<String, String>): Single<ProgressModel>
    @FormUrlEncoded
    @POST("addDate.php")
    fun UpdateDate (@FieldMap data: Map<String, String>): Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/registerUser.php")
    fun RegisterUser (@FieldMap data : Map<String, String>) : Single<RegisterModel>
    @FormUrlEncoded
    @POST("api/addqna.php")
    fun requestAddQna (@FieldMap data : Map<String, String>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/addGroup.php")
    fun UpdateGroup(@FieldMap data : Map<String, String>) : Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/updateChat.php")
    fun UpdateChat(@FieldMap data : Map<String, String>) : Single<UpdateModel>

    @FormUrlEncoded
    @POST("api/matchFast.php")
    fun requestFastMatch(@FieldMap data : Map<String, String>): Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/matchGroup.php")
    fun requestGroupMatch(@FieldMap data : Map<String, String>): Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/matchMentor.php")
    fun requestMentorMatch(@FieldMap data : Map<String, String>): Single<UpdateModel>
    @FormUrlEncoded
    @POST("api/productDetail.php")
    fun requestProductDetail(@FieldMap param: HashMap<String, String>): Single<ShopModel>

}