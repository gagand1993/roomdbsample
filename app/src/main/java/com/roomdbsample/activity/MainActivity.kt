package com.roomdbsample.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.roomdbsample.R
import com.roomdbsample.adapter.NotesListAdapter
import com.roomdbsample.databinding.ActivityMainBinding
import com.roomdbsample.other.GridSpacingItemDecoration
import com.roomdbsample.other.gone
import com.roomdbsample.other.visible
import com.roomdbsample.roomhelper.database.NotesDatabase
import com.roomdbsample.roomhelper.entity.Notes
import com.roomdbsample.viewmodel.MainViewModel
import com.roomdbsample.viewmodel.factory.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var userDatabase: NotesDatabase
    lateinit var adapter: NotesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel= ViewModelProvider(this,MainViewModelFactory(this))[MainViewModel::class.java]
        binding.mainviewmodel=mainViewModel
        userDatabase= NotesDatabase.getDatabaseInstance(this)


        setAdapter()
        mainViewModel.getAllUserResponse().observe(this) {
            it?.let {
                Log.i("userList", it.toString())
                adapter.updateList(it as ArrayList<Notes>)
                if (it.isEmpty()) {
                    binding.ivEmpty.visible()
                } else {
                    binding.ivEmpty.gone()
                }
            }
        }





        mainViewModel.getSingleUserResponse().observe(this, Observer {
            it?.let {
                    Log.e("singleUserData",it.toString())
            }


        })
        mainViewModel.getUpdateUserResponse().observe(this, Observer {

            it?.let {
                    Log.e("infoUpdatess",it.toString())
            }

        })



        binding.cvAdd.setOnClickListener {

            startActivity(Intent(this,AddNewNotesActivity::class.java))
        }


     }

    override fun onResume() {
        super.onResume()
        getAllUserData()
    }



    private fun getAllUserData() {
        mainViewModel.getAllUserDataRequest()

    }



    private fun getSingleUserData() {
        mainViewModel.getSingleUserDataRequest(2)
    }

    private fun setAdapter() {
//        userListAdapter=UserListAdapter(object : CallbackHelper {
//            override fun clickHandler(user: User) {
//                mainViewModel.deleteUserDataWithUserId(user)
//                getAllUserData()
//            }
//        })
//        binding.rvUserList.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
//        binding.rvUserList.adapter=userListAdapter

         adapter=NotesListAdapter()
        binding.rvNotes.layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val spanCount = 2 // 3 columns
        val spacing = 20 // 50px
        val includeEdge = false
        binding.rvNotes.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        binding.rvNotes.adapter=adapter
    }

}