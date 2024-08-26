package com.example.youtube.feature.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.youtube.R
import com.example.youtube.extension.formatted
import com.example.youtube.model.Video
import com.squareup.picasso.Picasso

class VideoDetailAdapter(var items: List<Video>): Adapter<VideoDetailAdapter.VideoDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_detail, parent, false)
        return VideoDetailViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VideoDetailViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class VideoDetailViewHolder(itemView: View): ViewHolder(itemView){
        fun bind(item: Video){
            with(itemView){
                Picasso.get().load(item.thumbnailUrl).into(findViewById<ImageView>(R.id.detail_item_thumb))
                findViewById<TextView>(R.id.detail_item_title).text = item.title
                findViewById<TextView>(R.id.detail_item_info).text = context.getString(
                    R.string.info,
                    item.publisher.name,
                    item.viewsCountLabel,
                    item.publishedAt.formatted()
                )
            }
        }
    }

}