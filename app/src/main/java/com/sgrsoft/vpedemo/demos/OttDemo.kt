package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * OTT 기능 데모.
 *
 * 플레이리스트 항목에 `intro`(start/duration) 를 주면 인트로 구간에 "스킵" 버튼이 노출되고,
 * `ageRating` 으로 연령등급/콘텐츠 경고 오버레이를 표시한다.
 */
@UnstableApi
@Composable
fun OttDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to true,
            "playlist" to listOf(
                mapOf(
                    "file" to "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8",
                    "intro" to mapOf("start" to "00:02", "duration" to 8.0),
                    "ageRating" to "15",
                ),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
