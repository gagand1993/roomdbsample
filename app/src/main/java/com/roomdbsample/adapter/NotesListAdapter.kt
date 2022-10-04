package com.roomdbsample.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.roomdbsample.R
import com.roomdbsample.databinding.RowNotesBinding
import com.roomdbsample.roomhelper.entity.Notes
import java.util.*


class NotesListAdapter(): RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    private var list=ArrayList<Notes>()
    class ViewHolder(var binding: RowNotesBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DataBindingUtil.inflate<RowNotesBinding>(LayoutInflater.from(parent.context), R.layout.row_notes,parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvTitle.text=list[position].title
        holder.binding.tvDescription.text=list[position].description


        holder.binding.clRoot.setCardBackgroundColor(getRandomColorCode());

    }
    private fun getRandomColorCode(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(notes: ArrayList<Notes>) {
        list=notes
        notifyDataSetChanged()
    }
}