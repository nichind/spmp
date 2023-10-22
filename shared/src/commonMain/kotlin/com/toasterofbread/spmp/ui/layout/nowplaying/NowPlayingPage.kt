package com.toasterofbread.spmp.ui.layout.nowplaying

import LocalPlayerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toasterofbread.spmp.ui.layout.apppage.mainpage.PlayerState
import com.toasterofbread.spmp.ui.layout.nowplaying.maintab.NowPlayingMainTabPage
import com.toasterofbread.spmp.ui.layout.nowplaying.queue.NowPlayingQueuePage

const val NOW_PLAYING_MAIN_PADDING = 10f

abstract class NowPlayingPage {
    @Composable
    abstract fun Page(page_height: Dp, top_bar: NowPlayingTopBar, modifier: Modifier)
    abstract fun shouldShow(player: PlayerState): Boolean

    @Composable
    fun getTopPadding(): Dp {
        val player: PlayerState = LocalPlayerState.current
        if (player.context.getStatusBarHeightDp() > 0.dp) {
            return NOW_PLAYING_MAIN_PADDING.dp / 2
        }
        return NOW_PLAYING_MAIN_PADDING.dp
    }
    fun getHorizontalPadding(): Dp = NOW_PLAYING_MAIN_PADDING.dp
    fun getBottomPadding(): Dp = NOW_PLAYING_MAIN_PADDING.dp

    companion object {
        val ALL: List<NowPlayingPage> =
            listOf(
                NowPlayingMainTabPage(),
                NowPlayingQueuePage()
            )
    }
}