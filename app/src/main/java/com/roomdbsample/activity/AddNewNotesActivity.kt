package com.roomdbsample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.roomdbsample.R
import com.roomdbsample.databinding.ActivityAddNewNotesBinding
import com.roomdbsample.other.gone
import com.roomdbsample.other.visible
import com.roomdbsample.roomhelper.database.NotesDatabase
import com.roomdbsample.viewmodel.MainViewModel
import com.roomdbsample.viewmodel.factory.MainViewModelFactory

class AddNewNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNewNotesBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var userDatabase: NotesDatabase
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
        binding.cvAdd.setOnClickListener {
            mainViewModel.addNotesData()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}