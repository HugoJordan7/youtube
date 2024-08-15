package com.example.youtube.feature.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.youtube.R

class MainAdapter(val list: List<String>): Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        //holder.bind(list[position])
    }

    inner class MainViewHolder(itemView: View): ViewHolder(itemView){
        fun bind(item: String){

        }
    }

}