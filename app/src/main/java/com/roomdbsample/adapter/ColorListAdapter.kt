package com.roomdbsample.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.roomdbsample.R
import com.roomdbsample.databinding.RowColorListsBinding
import com.roomdbsample.other.ColorModelHelper
import com.roomdbsample.other.gone
import com.roomdbsample.other.visible

class ColorListAdapter(var mContext: Context): RecyclerView.Adapter<ColorListAdapter.ViewHolder>() {

    var onItemClickListener: ((pos: String) -> Unit)? = null




    class ViewHolder(var binding:RowColorListsBinding):RecyclerView.ViewHolder(binding.root) {

    }

    var list= arrayListOf<ColorModelHelper>()
    fun updateList(colorList: ArrayList<ColorModelHelper>) {
        this.list=colorList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= DataBindingUtil.inflate<RowColorListsBinding>(LayoutInflater.from(parent.context), R.layout.row_color_lists,parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myColor = Color.parseColor(list[position].colorCode)
        holder.binding.ivColor.setColorFilter(myColor)

        if (list[position].isSelect){
            holder.binding.ivTick.visible()
        }else{
            holder.binding.ivTick.gone()

        }
        holder.itemView.setOnClickListener {
            list.forEach {
                it.isSelect=false
            }
            list[position].isSelect=true
            notifyDataSetChanged()
            onItemClickListener!!.invoke(list[position].colorCode)
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }
}