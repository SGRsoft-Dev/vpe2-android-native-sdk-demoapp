package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * Now Playing(미디어 세션) 데모.
 *
 * `enableNowPlayingPlaybackState=true` 면 MediaSession 으로 잠금화면/알림에 재생 상태와 메타데이터를 노출한다.
 * 메타데이터는 플레이리스트 항목의 `description`(title/profile_name 등)에서 가져온다.
 */
@UnstableApi
@Composable
fun NowPlayingDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "enableNowPlayingPlaybackState" to true,
            "playlist" to listOf(
                mapOf(
                    "file" to "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    "description" to mapOf("title" to "Big Buck Bunny", "profile_name" to "Blender"),
                ),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
