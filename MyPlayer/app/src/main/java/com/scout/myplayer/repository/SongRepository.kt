package com.scout.myplayer.repository

import android.content.Context
import android.media.MediaMetadataRetriever
import com.scout.myplayer.data.Song
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepository @Inject constructor(@ApplicationContext context: Context){

    init
    {
        val assetManager = context.assets
        try
        {
            var files = assetManager.list("music")
            for(i in files!!.indices)
            {
                val afd = assetManager.openFd("music/"+files[i])
                val metadataRetriever = MediaMetadataRetriever()
                metadataRetriever.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
                val title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                val artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                val duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                songs.add(i, Song(id = i, fileName = "music/"+files[i], title?:"",duration?:"",artist?:""))
            }
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
    }

    companion object
    {
        var songs: ArrayList<Song> = ArrayList()
    }
    val playlist
        get() = songs
}