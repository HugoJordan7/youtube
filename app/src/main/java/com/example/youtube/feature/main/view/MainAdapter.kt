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

class MainAdapter(
    var items: List<Video>,
    private val onClick: (Video) -> Unit
): Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainViewHolder(itemView: View): ViewHolder(itemView){
        fun bind(item: Video){
            with(itemView){
                setOnClickListener{
                    onClick.invoke(item)
                }
                Picasso.get().load(item.videoUrl).into(findViewById<ImageView>(R.id.video_thumbnail))
                Picasso.get().load(item.publisher.pictureProfileUrl).into(findViewById<ImageView>(R.id.video_author))
                findViewById<TextView>(R.id.video_title).text = item.title
                findViewById<TextView>(R.id.video_info).text = context.getString(
                    R.string.info,
                    item.publisher.name,
                    item.viewsCountLabel,
                    item.publishedAt.formatted()
                )
            }
        }
    }

}