package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * ObjectFit(비디오 채움 모드) 데모.
 *
 * `objectFit="cover"` 면 종횡비를 유지한 채 뷰를 가득 채우도록 확대(가장자리 크롭)한다.
 * 다른 값: "contain"(기본, 레터박스) · "fill"/"stretch"(왜곡) · "scale-down".
 */
@UnstableApi
@Composable
fun ObjectFitDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to true,
            "objectFit" to "cover",
            "playlist" to listOf(
                mapOf("file" to "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8"),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
