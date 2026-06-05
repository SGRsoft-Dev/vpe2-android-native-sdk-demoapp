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
 * `enableNowPlayingPlaybackState=true` 면 MediaSession 으로 잠금화면/미디어 알림에 재생 상태와 메타데이터를 노출한다.
 * - 메타데이터: 플레이리스트 항목의 `description.title`(제목) · `description.profile_name`(아티스트) · `poster`(아트워크).
 * - `staysActiveInBackground=true` + `autoPause=false` → 백그라운드(홈/잠금)에서도 재생 유지 → 잠금화면 컨트롤 동작.
 */
@UnstableApi
@Composable
fun NowPlayingDemo() = DemoPlayerScaffold(
    note = "재생 후 홈/잠금화면으로 가면 미디어 알림·잠금화면에 제목/아티스트/아트워크와 재생 컨트롤이 표시됩니다.",
) { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to false,
            "staysActiveInBackground" to true,
            "autoPause" to false,
            "enableNowPlayingPlaybackState" to true,
            "playlist" to listOf(
                mapOf(
                    "file" to "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8",
                    "poster" to "https://tkmenfxu2702.edge.naverncp.com/profile/202605/25e7fe682c76cbbb31e7e6fc79a653ac.png",
                    "description" to mapOf(
                        "title" to "1편 — 네이버클라우드 소개",
                        "profile_name" to "네이버클라우드",
                    ),
                ),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
