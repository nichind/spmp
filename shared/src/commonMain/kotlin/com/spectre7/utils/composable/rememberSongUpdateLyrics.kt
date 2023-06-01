package com.spectre7.utils.composable

import androidx.compose.runtime.*
import com.spectre7.spmp.model.mediaitem.Song
import com.spectre7.utils.launchSingle

@Composable
fun rememberSongUpdateLyrics(song: Song?, update_lyrics: Boolean = true): State<Song?> {
    val current_song: MutableState<Song?> = remember { mutableStateOf(song) }
    val coroutine_scope = rememberCoroutineScope()
    val reg_lyrics_listener: (Pair<Int, Song.Lyrics.Source>?) -> Unit = remember(update_lyrics) { { data ->
        if (update_lyrics) {
            val lyrics_holder = current_song.value!!.lyrics
            if (data?.first != lyrics_holder.lyrics?.id || data?.second != lyrics_holder.lyrics?.source) {
                coroutine_scope.launchSingle { lyrics_holder.loadAndGet() }
            }
        }
    } }

    DisposableEffect(song) {
        current_song.value = song?.apply {
            song_reg_entry.lyrics_listeners.add(reg_lyrics_listener)
            if (update_lyrics) {
                coroutine_scope.launchSingle { lyrics.loadAndGet() }
            }
        }

        onDispose {
            current_song.value?.apply { song_reg_entry.lyrics_listeners.remove(reg_lyrics_listener) }
        }
    }

    return current_song
}