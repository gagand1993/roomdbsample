package com.roomdbsample.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.roomdbsample.R
import com.roomdbsample.adapter.NotesGridListAdapter
import com.roomdbsample.adapter.NotesListAdapter
import com.roomdbsample.base.ApplicationHelper.Companion.prefs
import com.roomdbsample.databinding.ActivityMainBinding
import com.roomdbsample.other.AppConstants
import com.roomdbsample.other.AppConstants.GRID_VIEW
import com.roomdbsample.other.AppConstants.IS_MENU_SHOW
import com.roomdbsample.other.AppConstants.LIST_VIEW
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
    lateinit var adapterGrid: NotesGridListAdapter
    lateinit var adapterList: NotesListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel= ViewModelProvider(this,MainViewModelFactory(this))[MainViewModel::class.java]
        binding.mainviewmodel=mainViewModel
        userDatabase= NotesDatabase.getDatabaseInstance(this)


         mainViewModel.getAllUserResponse().observe(this) {
            it?.let {
                Log.i("userList", it.toString())
               /* if (prefs!!.getString(IS_MENU_SHOW) == AppConstants.LIST_VIEW){

                }else{
                    adapterGrid.updateList(it as ArrayList<Notes>)

                }*/
                if (it.isEmpty()) {
                    binding.ivEmpty.visible()
                } else {
                    binding.ivEmpty.gone()
                }

                if (prefs!!.getString(IS_MENU_SHOW) == AppConstants.LIST_VIEW){

                    adapterList= NotesListAdapter(it as ArrayList<Notes>)
                    binding.rvNotes.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL,false)

                    binding.rvNotes.adapter=adapterList
                    adapterList.onItemClickListener={
                        startActivity(Intent(this@MainActivity,AddNewNotesActivity::class.java).apply {
                            putExtra("id",it.id.toString())
                            putExtra("title", it.title)
                            putExtra("description",it.description)
                            putExtra("backgroundType",it.backgroundType)
                            putExtra("background",it.background)
                        })
                    }
                }else{

                    adapterGrid=NotesGridListAdapter(it as ArrayList<Notes>)
                    binding.rvNotes.layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


                    binding.rvNotes.adapter=adapterGrid

                    adapterGrid.onItemClickListener={
                        startActivity(Intent(this@MainActivity,AddNewNotesActivity::class.java).apply {
                            putExtra("id",it.id.toString())
                            putExtra("title", it.title)
                            putExtra("description",it.description)
                            putExtra("backgroundType",it.backgroundType)
                            putExtra("background",it.background)
                        })
                    }
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


        binding.ivOption.setOnClickListener {
            val popup = PopupMenu(this, it)
             val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.option_create, popup.menu)
            popup.setOnMenuItemClickListener { item ->

                when(item.itemId){

                    R.id.menu_list_view->{

                        prefs!!.saveString(IS_MENU_SHOW, LIST_VIEW)
                        getAllUserData()
                    }

                    R.id.menu_grid_view->{

                        prefs!!.saveString(IS_MENU_SHOW, GRID_VIEW)

                        getAllUserData()
                    }

                }

                true
            }


            popup.show()
        }

        binding.fbAdd.setOnClickListener {

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



}