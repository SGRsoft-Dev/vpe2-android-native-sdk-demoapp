package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * 자막(SRT) 데모 — 외부 사이드카 SRT 자막.
 *
 * VTT 대신 `srt` 배열로 SubRip(.srt) 트랙을 지정한다(스키마는 vtt 와 동일: id/file/label/default).
 * vtt 가 비어 있으면 `effectiveSubtitles` 가 srt 를 사용한다.
 */
@UnstableApi
@Composable
fun SrtDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
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
                    "srt" to listOf(
                        mapOf("id" to "ko", "file" to "https://player.vpe.naverncp.com/srt/ncp_overview_script_kr_v2.srt", "label" to "한국어", "default" to true),
                        mapOf("id" to "en", "file" to "https://player.vpe.naverncp.com/srt/ncp_overview_script_en_v2.srt", "label" to "English"),
                    ),
                ),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
