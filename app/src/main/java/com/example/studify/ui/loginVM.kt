package com.example.studify.ui

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.Tool.Preferences
import com.example.studify.data.StudifyService
import com.example.studify.data.model.LoginModel
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import javax.inject.Inject


@HiltViewModel
class loginVM @Inject constructor(application: Application,private val userRepository: UserRepository): ViewModel() {
    var loginid =mutableStateOf<String>("admin")
    var password = mutableStateOf<String>("admin")
    var autologin=mutableStateOf<Boolean>(false)
    var loginerror = mutableStateOf<String>("")
    var loginsuccess = mutableStateOf(false)


    init {
        if (Preferences.getBoolean("AUTOLOGIN")){
            requestlogin(id = Preferences.getString("ID").toString(),token = Preferences.getString("TOKEN").toString() , pwd= "")
        }

    }

    fun requestlogin(id :String = loginid.value, pwd: String = password.value ,token :String ="" ) = userRepository.requestLogin(id ,pwd, token)
        .subscribe ({
            loginModel ->
            if (loginModel.resultCode == "200"){
                Preferences.putLong("USERID", loginModel.result!!.userid!!)
                Preferences.putString("ID", loginModel.result!!.id.toString())
                Preferences.putString("USERNAME", loginModel.result!!.username.toString())
                Preferences.putInt("SEX", loginModel.result!!.sex!! )
                Preferences.putString("GROUPLIST", loginModel.result!!.grouplist.toString())
                Preferences.putFloat("TENDENCY", loginModel.result!!.tendency)
                Preferences.putInt("REPORT", loginModel.result!!.report!!)
                Preferences.putString("ADDRESS", loginModel.result!!.address.toString())
                Preferences.putString("EMAIL", loginModel.result!!.email.toString())
                Preferences.putBoolean("AUTOLOGIN", autologin.value)
                Preferences.putString("access_token", loginModel.result!!.token)
                Log.d("access_token" , Preferences.getString("access_token").toString())
                loginsuccess.value = true
            }
            else if(loginModel.resultCode =="400"){
                loginerror.value = loginModel.errorMsg
            }
        },{
            error->
            loginerror.value= error.toString()
        }
        )
}
