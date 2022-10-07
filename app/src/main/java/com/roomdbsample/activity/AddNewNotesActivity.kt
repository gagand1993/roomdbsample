package com.roomdbsample.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roomdbsample.R
import com.roomdbsample.adapter.ColorListAdapter
import com.roomdbsample.databinding.ActivityAddNewNotesBinding
import com.roomdbsample.other.ColorModelHelper
import com.roomdbsample.other.gone
import com.roomdbsample.other.visible
import com.roomdbsample.roomhelper.database.NotesDatabase
import com.roomdbsample.viewmodel.MainViewModel
import com.roomdbsample.viewmodel.factory.MainViewModelFactory

class AddNewNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNewNotesBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var userDatabase: NotesDatabase

    var id=""
    var title=""
    var description=""

    var backgroundType="0"
    var colorList= arrayListOf(
        ColorModelHelper("#000000",true),
        ColorModelHelper("#FF0000",false),
        ColorModelHelper("#00FFFF",false),
        ColorModelHelper("#0000FF",false),
        ColorModelHelper("#ADD8E6",false),
        ColorModelHelper("#FF00FF",false)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_add_new_notes)
        mainViewModel= ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]
        binding.mainviewmodel=mainViewModel
        userDatabase= NotesDatabase.getDatabaseInstance(this)


        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.etTitle.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if (s.toString().isEmpty()||binding.etDescription.text.toString().trim().isEmpty()){
                    binding.cvAdd.gone()
                }else{
                    binding.cvAdd.visible()
                }
             }

        })

        binding.etDescription.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if (s.toString().isEmpty()||binding.etTitle.text.toString().trim().isEmpty()){
                    binding.cvAdd.gone()
                }else{
                    binding.cvAdd.visible()
                }
            }

        })

        mainViewModel.getAddUserResponse().observe(this, Observer {
            it?.let {
                onBackPressed()

            }
        })
        mainViewModel.getUpdateUserResponse().observe(this, Observer {

            it?.let {
                onBackPressed()
            }

        })

        binding.cvAdd.setOnClickListener {
            if (id.isNotEmpty()){
                val colorGet=colorList.filter { it.isSelect }

                mainViewModel.updateUserClick(id.toLong(),backgroundType,colorGet[0].colorCode)
            }else{
                val colorGet=colorList.filter { it.isSelect }

                mainViewModel.addNotesData(backgroundType,colorGet[0].colorCode)

            }
        }

        if (intent.getStringExtra("id")!=null){
            binding.tvTitle.text=getString(R.string.edit)
            id=intent.getStringExtra("id")!!
            title=intent.getStringExtra("title")!!
            description=intent.getStringExtra("description")!!
            backgroundType=intent.getStringExtra("backgroundType")!!
            val background=intent.getStringExtra("background")!!

            colorList.forEach {
                it.isSelect=false
            }
            colorList.forEach {
                if (background==it.colorCode){
                    it.isSelect=true
                    binding.clRoot.setBackgroundColor(Color.parseColor(background))

                }
            }

            mainViewModel.title.set(title)
            mainViewModel.description.set(description)

        }


        colorListAdapter()


    }

    private fun colorListAdapter() {

        val adapter=ColorListAdapter(this)
        binding.rvColor.layoutManager=LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        binding.rvColor.adapter=adapter
        adapter.updateList(colorList)
        adapter.onItemClickListener={
            binding.clRoot.setBackgroundColor(Color.parseColor(it))

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}