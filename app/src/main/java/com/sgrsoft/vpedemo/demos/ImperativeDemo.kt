package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayerController
import com.navercloud.vpe.player.ui.VpePlayerView
import com.sgrsoft.vpedemo.DemoActionButton
import com.sgrsoft.vpedemo.DemoPlayerScaffold
import com.sgrsoft.vpedemo.DemoTheme

/**
 * Imperative(명령형) 제어 데모 — 컨트롤러 직접 보유 + 커스텀 컨트롤.
 *
 * 1) `VpePlayerController.fromMap(...)` 로 컨트롤러를 직접 만들어 `remember` 로 보유한다.
 *    (`controls=false` 로 내장 컨트롤을 끄고 직접 버튼 UI 를 구성)
 * 2) `VpePlayerView(controller, showBuiltinControls = false)` 로 화면만 렌더.
 * 3) `controller.play()/pause()/seekTo()` 등 명령형 API 로 제어하고,
 *    `controller.state`(StateFlow)를 구독해 현재 재생 상태를 표시한다.
 */
@UnstableApi
@Composable
fun ImperativeDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    val context = LocalContext.current
    // accessKey/platform/stage 가 바뀌면(적용 시) 컨트롤러를 재생성한다.
    val controller = remember(accessKey, platform, stage) {
        VpePlayerController.fromMap(
            context = context,
            options = mapOf(
                "autostart" to false,
                "controls" to false,
                "aspectRatio" to "16:9",
                "playlist" to listOf(
                    mapOf("file" to "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8"),
                ),
            ),
            accessKey = accessKey,
            platform = platform,
            stage = stage,
        )
    }
    DisposableEffect(controller) { onDispose { controller.destroy() } }
    val state by controller.state.collectAsStateWithLifecycle()

    Column(Modifier.padding(8.dp)) {
        VpePlayerView(controller = controller, showBuiltinControls = false, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DemoActionButton("재생") { controller.play() }
            DemoActionButton("일시정지") { controller.pause() }
            DemoActionButton("+10s") { controller.seekTo(state.currentTime + 10) }
        }
        Text(
            if (state.isPlaying) "재생중 ${state.currentTime.toInt()}s" else "정지",
            color = DemoTheme.textSecondary, modifier = Modifier.padding(top = 8.dp),
        )
    }
}
