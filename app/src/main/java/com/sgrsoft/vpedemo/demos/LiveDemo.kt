package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * 라이브 스트림 데모.
 *
 * 라이브(.m3u8 무한 스트림)는 SDK 가 자동 감지하여 LIVE 레이아웃(빨간 점 + "LIVE", 진행바 숨김)으로 표시한다.
 * 별도 플래그 없이 `file` 만 지정하면 된다.
 */
@UnstableApi
@Composable
fun LiveDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to true,
            "playlist" to listOf(
                mapOf("file" to "https://stream.mux.com/v69RSHhFelSm4701snP22dYz2jICy4E4FUyk02rW4gxRM.m3u8"),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
