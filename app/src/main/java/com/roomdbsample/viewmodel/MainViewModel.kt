package com.roomdbsample.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roomdbsample.roomhelper.database.UserDatabase
import com.roomdbsample.roomhelper.entity.User
import kotlinx.coroutines.*
import java.util.*

class MainViewModel(val context: Context) :ViewModel(){


    var name=ObservableField("")
    var phone=ObservableField("")

    fun addUserClick(view: View){
        getInsertUserDataRequest(User(0, name = name.get().toString(), phone = phone.get().toString(), Date(),
            Date()
        ))
    }

    fun updateUserClick(view: View){
        GlobalScope.launch(Dispatchers.Main) {

            val job=   GlobalScope.launch (Dispatchers.Main) {
                UserDatabase.getDatabaseInstance(context).userDao().updateInfo(name.get().toString(),Date(), 1)

            }
            job.join()
            updateUserMutableLiveData.value="update"

        }

    }


    private val addUserMutableLiveData = MutableLiveData<User>()

    fun getAddUserResponse(): LiveData<User> {
        return addUserMutableLiveData
    }



    private val updateUserMutableLiveData = MutableLiveData<String>()

    fun getUpdateUserResponse(): LiveData<String> {
        return updateUserMutableLiveData
    }

    private fun getInsertUserDataRequest(user:User){
        GlobalScope.launch {
            val job =  GlobalScope.launch(Dispatchers.Main) {
                UserDatabase.getDatabaseInstance(context).userDao().insert(user)
            }

            job.join()
            getAllUserDataRequest()
        }



    }



    private val singleUserMutableLiveData = MutableLiveData<User?>()

    fun getSingleUserResponse(): LiveData<User?> {
        return singleUserMutableLiveData
    }


    fun getSingleUserDataRequest(id:Long){
        GlobalScope.launch(Dispatchers.Main) {


            var user:User?=null
            val job=  GlobalScope.launch (Dispatchers.IO) {
                user=  UserDatabase.getDatabaseInstance(context).userDao().getSingleUser(id)

            }
            job.join()
            singleUserMutableLiveData.value= user

        }
    }

    private val allUserMutableLiveData = MutableLiveData<List<User?>>()

    fun getAllUserResponse(): LiveData<List<User?>> {
        return allUserMutableLiveData
    }
    fun getAllUserDataRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            val user = UserDatabase.getDatabaseInstance(context).userDao().getAllUser()
            allUserMutableLiveData.value=user
            allUserMutableLiveData.value=null

        }
    }

    fun deleteUserDataWithUserId(user:User){
        GlobalScope.launch {
            val job =  GlobalScope.launch(Dispatchers.Main) {
                UserDatabase.getDatabaseInstance(context).userDao().deleteUser(user.id)
            }
            job.join()
            getAllUserDataRequest()
        }

    }

}