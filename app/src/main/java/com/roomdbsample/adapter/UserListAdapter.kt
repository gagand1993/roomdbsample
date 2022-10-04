package com.roomdbsample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.roomdbsample.R
import com.roomdbsample.databinding.RowUserBinding
import com.roomdbsample.other.CallbackHelper
import com.roomdbsample.roomhelper.entity.User
import java.util.*


class UserListAdapter(val callback: CallbackHelper): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var list=ArrayList<User>()

    class ViewHolder(var binding: RowUserBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DataBindingUtil.inflate<RowUserBinding>(LayoutInflater.from(parent.context), R.layout.row_user,parent,false)
        return ViewHolder(binding)
    }

    fun updateList(list:ArrayList<User>){
        this.list=list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvName.text=list[position].name
        holder.binding.tvPhone.text=list[position].phone

        holder.binding.ivDelete.setOnClickListener {
            callback.clickHandler(list[position])
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }
}