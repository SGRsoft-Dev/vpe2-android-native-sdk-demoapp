package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * 워터마크 데모.
 *
 * `visibleWatermark=true` + `watermarkText` 로 텍스트 워터마크를 표시한다.
 * `watermarkConfig.randPosition=true` 면 `randPositionInterVal`(ms) 주기로 위치가 무작위 이동한다.
 * (고정 위치는 randPosition=false + x/y(%) 지정)
 */
@UnstableApi
@Composable
fun WatermarkDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to true,
            "visibleWatermark" to true,
            "watermarkText" to "NAVER CLOUD PLATFORM",
            "watermarkConfig" to mapOf(
                "randPosition" to true,
                "randPositionInterVal" to 3000,
                "opacity" to 0.3,
            ),
            "playlist" to listOf(
                mapOf("file" to "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8"),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
