package com.roomdbsample.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.roomdbsample.R
import com.roomdbsample.databinding.RowNotesGridBinding
import com.roomdbsample.roomhelper.entity.Notes
import java.util.*


class NotesGridListAdapter(var list:ArrayList<Notes>): RecyclerView.Adapter<NotesGridListAdapter.ViewHolder>() {

    var onItemClickListener: ((notes:Notes) -> Unit)? = null
    class ViewHolder(var binding: RowNotesGridBinding):RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=DataBindingUtil.inflate<RowNotesGridBinding>(LayoutInflater.from(parent.context), R.layout.row_notes_grid,parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvTitle.text=list[position].title
        holder.binding.tvDescription.text=list[position].description
        holder.binding.clRoot.setCardBackgroundColor(Color.parseColor(list[position].background))
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(list[position])
        }



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