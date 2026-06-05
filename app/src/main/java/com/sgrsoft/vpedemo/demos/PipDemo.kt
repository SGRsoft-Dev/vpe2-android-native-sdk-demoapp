package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * Picture-in-Picture(PiP) 데모.
 *
 * - `allowsPictureInPicture=true` → SDK 가 Activity PiP 파라미터에 자동 진입(setAutoEnterEnabled)을 설정.
 *   재생 중 **홈 버튼/백그라운드로 빠지면 OS 가 자동으로 PiP 창으로 전환**한다(API 31+).
 * - `staysActiveInBackground=true` + `autoPause=false` → 백그라운드/PiP 에서도 재생 유지.
 * - PiP 중에는 SDK 컨트롤과 데모 크롬(앱바·설정)이 숨겨지고 비디오만 표시된다.
 *
 * 필요: 호스트 매니페스트 `android:supportsPictureInPicture="true"` (데모앱 적용됨).
 */
@UnstableApi
@Composable
fun PipDemo() = DemoPlayerScaffold(
    note = "재생 중 홈 버튼을 누르면(백그라운드 전환) 자동으로 PiP 창으로 전환됩니다.",
) { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to false,
            "allowsPictureInPicture" to true,
            "staysActiveInBackground" to true,
            "autoPause" to false,
            "playlist" to listOf(
                mapOf("file" to "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8"),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
