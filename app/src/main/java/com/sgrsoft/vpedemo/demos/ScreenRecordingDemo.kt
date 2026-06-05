package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * 화면 녹화/캡처 방지 데모.
 *
 * `screenRecordingPrevention=true` 면 윈도우에 FLAG_SECURE 가 적용되어 스크린샷·화면 녹화·미러링이 차단된다.
 * (캡처 시도 시 검은 화면 또는 차단 오버레이)
 */
@UnstableApi
@Composable
fun ScreenRecordingDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to true,
            "screenRecordingPrevention" to true,
            "playlist" to listOf(
                mapOf("file" to "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8"),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
