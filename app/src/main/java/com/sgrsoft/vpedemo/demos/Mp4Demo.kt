package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * 프로그레시브 MP4 재생 데모.
 *
 * 단일 .mp4 파일 재생. 별도 옵션 없이 `file` 만 지정한다.
 */
@UnstableApi
@Composable
fun Mp4Demo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to true,
            "playlist" to listOf(
                mapOf("file" to "https://2ardrvaj2252.edge.naverncp.com/endpoint/sample/221027_NAVER_Cloud_intro_Long_ver_AVC_FHD_2Pass_30fps.mp4"),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
