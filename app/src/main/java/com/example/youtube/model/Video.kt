package com.example.youtube.model

import com.example.youtube.extension.toDate
import java.util.Date

data class Video(
    val id: String,
    val thumbnailUrl: String,
    val title: String,
    val publishedAt: Date,
    val viewsCount: Long,
    val viewsCountLabel: String,
    val duration: Int,
    val videoUrl: String,
    val publisher: Publisher
)

class VideoBuilder{
    var id: String = ""
    var thumbnailUrl: String = ""
    var title: String = ""
    var publishedAt: Date = Date()
    var viewsCount: Long = 0
    var viewsCountLabel: String = ""
    var duration: Int = 0
    var videoUrl: String = ""
    var publisher: Publisher = PublisherBuilder().build()

    fun build(): Video{
        return Video(
            id, thumbnailUrl, title, publishedAt, viewsCount,
            viewsCountLabel, duration, videoUrl, publisher
        )
    }

    fun publisher(block: PublisherBuilder.() -> Unit): Publisher{
        return PublisherBuilder().apply(block).build()
    }

}

fun video(block: VideoBuilder.() -> Unit): Video{
    return VideoBuilder().apply(block).build()
}

val videos = listOf(
    video {
        id = "UVpKBHO2fMg"
        thumbnailUrl = "https://img.youtube.com/vi/UVpKBHO2fMg/maxresdefault.jpg"
        title = "Entrevista com Marlon Wayans | The Noite (14/08/19)"
        viewsCount = 742497
        publishedAt = "2019-08-15".toDate()
        viewsCountLabel = "7M"
        duration = 1886
        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        publisher {
            id = "sbtthenoite"
            name = "The Noite com Danilo Gentili"
            pictureProfileUrl = "https://yt3.ggpht.com/a/AGF-l7_3BYlSlp94WOjGe1UECUCdb73qRJVFH_t9Tw=s48-c-k-c0xffffffff-no-rj-mo"
        }
    },
    video {
        id = "UVpKBHO2fMg"
        thumbnailUrl = "https://img.youtube.com/vi/UVpKBHO2fMg/maxresdefault.jpg"
        title = "Entrevista com Marlon Wayans | The Noite (14/08/19)"
        viewsCount = 742497
        publishedAt = "2019-08-15".toDate()
        viewsCountLabel = "7M"
        duration = 1886
        videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        publisher {
            id = "sbtthenoite"
            name = "The Noite com Danilo Gentili"
            pictureProfileUrl = "https://yt3.ggpht.com/a/AGF-l7_3BYlSlp94WOjGe1UECUCdb73qRJVFH_t9Tw=s48-c-k-c0xffffffff-no-rj-mo"
        }
    }
)