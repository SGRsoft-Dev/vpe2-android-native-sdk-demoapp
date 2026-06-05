package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * DASH 재생 데모.
 *
 * Android(ExoPlayer)는 DASH(.mpd) 를 직접 재생한다 — iOS 미지원 대비 Android 고유 기능.
 * `file` 에 .mpd 매니페스트 URL 만 지정하면 된다.
 */
@UnstableApi
@Composable
fun DashDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to true,
            "playlist" to listOf(
                mapOf("file" to "https://dash.akamaized.net/akamai/bbb_30fps/bbb_30fps.mpd"),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
