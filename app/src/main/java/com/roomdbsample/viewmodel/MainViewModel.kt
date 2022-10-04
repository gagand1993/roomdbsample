package com.roomdbsample.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roomdbsample.roomhelper.database.NotesDatabase
import com.roomdbsample.roomhelper.entity.Notes
import kotlinx.coroutines.*
import java.util.*

class MainViewModel(val context: Context) :ViewModel(){


    var title=ObservableField("")
    var description=ObservableField("")

    fun addNotesData(){
        getInsertUserDataRequest(Notes(0, title = title.get().toString(), description = description.get().toString(), Date(),
            Date()
        ))
    }

    fun updateUserClick(view: View){
        GlobalScope.launch(Dispatchers.Main) {

            val job=   GlobalScope.launch (Dispatchers.Main) {
                NotesDatabase.getDatabaseInstance(context).userDao().updateInfo(title.get().toString(),Date(), 1)

            }
            job.join()
            updateUserMutableLiveData.value="update"

        }

    }


    private val addUserMutableLiveData = MutableLiveData<String>()

    fun getAddUserResponse(): LiveData<String> {
        return addUserMutableLiveData
    }



    private val updateUserMutableLiveData = MutableLiveData<String>()

    fun getUpdateUserResponse(): LiveData<String> {
        return updateUserMutableLiveData
    }

    private fun getInsertUserDataRequest(user:Notes){
        GlobalScope.launch(Dispatchers.Main) {
            val job =  GlobalScope.async (Dispatchers.Main) {
                NotesDatabase.getDatabaseInstance(context).userDao().insert(user)
            }

            job.await()
             addUserMutableLiveData.value="Add Notes"
        }



    }



    private val singleUserMutableLiveData = MutableLiveData<Notes?>()

    fun getSingleUserResponse(): LiveData<Notes?> {
        return singleUserMutableLiveData
    }


    fun getSingleUserDataRequest(id:Long){
        GlobalScope.launch(Dispatchers.Main) {


            var user:Notes?=null
            val job=  GlobalScope.launch (Dispatchers.IO) {
                user=  NotesDatabase.getDatabaseInstance(context).userDao().getSingleUser(id)

            }
            job.join()
            singleUserMutableLiveData.value= user

        }
    }

    private val allUserMutableLiveData = MutableLiveData<List<Notes?>>()

    fun getAllUserResponse(): LiveData<List<Notes?>> {
        return allUserMutableLiveData
    }
    fun getAllUserDataRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            val user = NotesDatabase.getDatabaseInstance(context).userDao().getAllUser()
            allUserMutableLiveData.value=user
            allUserMutableLiveData.value=null

        }
    }

    fun deleteUserDataWithUserId(user:Notes){
        GlobalScope.launch {
            val job =  GlobalScope.launch(Dispatchers.Main) {
                NotesDatabase.getDatabaseInstance(context).userDao().deleteUser(user.id)
            }
            job.join()
            getAllUserDataRequest()
        }

    }

}