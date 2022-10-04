package com.roomdbsample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roomdbsample.R
import com.roomdbsample.adapter.UserListAdapter
import com.roomdbsample.databinding.ActivityMainBinding
import com.roomdbsample.other.CallbackHelper
import com.roomdbsample.roomhelper.database.UserDatabase
import com.roomdbsample.roomhelper.entity.User
import com.roomdbsample.viewmodel.MainViewModel
import com.roomdbsample.viewmodel.factory.MainViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var userDatabase: UserDatabase
    lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel= ViewModelProvider(this,MainViewModelFactory(this))[MainViewModel::class.java]
        binding.mainviewmodel=mainViewModel
        userDatabase= UserDatabase.getDatabaseInstance(this)


        setAdapter()
        mainViewModel.getAllUserResponse().observe(this, Observer {
            it?.let {
                Log.i("userList",it.toString())
                userListAdapter.updateList(it as ArrayList<User>)
            }
        })

        mainViewModel.getAddUserResponse().observe(this, Observer {
            it?.let {
                getAllUserData()

            }
        })



        mainViewModel.getSingleUserResponse().observe(this, Observer {
            it?.let {
                    Log.e("singleUserData",it.toString())
            }


        })
        mainViewModel.getUpdateUserResponse().observe(this, Observer {

            it?.let {
                    Log.e("infoUpdate",it.toString())
            }


        })




        getAllUserData()
        getSingleUserData()




     }



    private fun getAllUserData() {
        mainViewModel.getAllUserDataRequest()

    }



    private fun getSingleUserData() {
        mainViewModel.getSingleUserDataRequest(2)
    }

    private fun setAdapter() {
        userListAdapter=UserListAdapter(object : CallbackHelper {
            override fun clickHandler(user: User) {
                mainViewModel.deleteUserDataWithUserId(user)
                getAllUserData()
            }
        })
        binding.rvUserList.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.rvUserList.adapter=userListAdapter
    }

}