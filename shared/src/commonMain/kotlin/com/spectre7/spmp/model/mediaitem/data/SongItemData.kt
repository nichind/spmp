package com.spectre7.spmp.model.mediaitem.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.beust.klaxon.Klaxon
import com.spectre7.spmp.model.mediaitem.AccountPlaylist
import com.spectre7.spmp.model.mediaitem.Playlist
import com.spectre7.spmp.model.mediaitem.Song
import com.spectre7.spmp.model.mediaitem.enums.SongType

class SongItemData(override val data_item: Song): MediaItemData(data_item) {

    var song_type: SongType? by mutableStateOf(null)
        private set

    fun supplySongType(value: SongType?, certain: Boolean = false, cached: Boolean = false): Song {
        if (value != song_type && (song_type == null || certain)) {
            song_type = value
            onChanged(cached)
        }
        return data_item
    }

    var duration: Long? by mutableStateOf(null)
        private set

    fun supplyDuration(value: Long?, certain: Boolean = false, cached: Boolean = false): Song {
        if (value != duration && (duration == null || certain)) {
            duration = value
            onChanged(cached)
        }
        return data_item
    }

    var album: Playlist? by mutableStateOf(null)
        private set

    fun supplyAlbum(value: Playlist?, certain: Boolean = false, cached: Boolean = false): Song {
        if (value != album && (album == null || certain)) {
            album = value
            onChanged(cached)
        }
        return data_item
    }

    override fun getSerialisedData(klaxon: Klaxon): List<String> {
        return super.getSerialisedData(klaxon) + listOf(klaxon.toJsonString(song_type?.ordinal), klaxon.toJsonString(duration), klaxon.toJsonString(album?.id))
    }

    override fun supplyFromSerialisedData(data: MutableList<Any?>, klaxon: Klaxon): MediaItemData {
        require(data.size >= 3)
        data.removeLast()?.also { supplyAlbum(AccountPlaylist.fromId(it as String), cached = true) }
        data.removeLast()?.also { supplyDuration((it as Int).toLong(), cached = true) }
        data.removeLast()?.also { supplySongType(SongType.values()[it as Int], cached = true) }
        return super.supplyFromSerialisedData(data, klaxon)
    }
}