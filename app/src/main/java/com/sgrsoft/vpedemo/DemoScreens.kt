package com.sgrsoft.vpedemo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.navercloud.vpe.player.VpePlayerController
import com.navercloud.vpe.player.ui.VpePlayerView

/** 데모 시나리오 (iOS VPEDemo 14개 View 대응). */
enum class DemoScenario(val title: String, val desc: String) {
    BASIC("Basic (Dict)", "Map 옵션 + HLS 자동재생"),
    BASIC_JSON("Basic (JSON)", "느슨한 JSON 문자열 옵션"),
    IMPERATIVE("Imperative Control", "컨트롤러 직접 보유 + 커스텀 버튼"),
    LIVE("Live Stream", "라이브 자동 감지"),
    WATERMARK("Watermark", "무작위 이동 워터마크"),
    OTT("OTT Skip", "intro/opening/ending 스킵"),
    SUBTITLE_VTT("VTT Subtitle", "외부 사이드카 자막"),
    DASH("DASH", "ExoPlayer DASH 재생 (iOS 미지원)"),
    MP4("MP4", "프로그레시브 MP4"),
    DRM("DRM (Widevine)", "헤더 패스스루 DRM"),
    SCREEN_REC("Screen Recording Prevention", "FLAG_SECURE 차단"),
    NOW_PLAYING("Now Playing", "MediaSession 메타데이터"),
    OBJECT_FIT("ObjectFit cover", "비디오 채움 모드"),
    REMOTE_API("Remote API", "서버 옵션 JSON 수신(개념)");
}

@Composable
fun DemoHome(onSelect: (DemoScenario) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Column {
                Text("VPE Android SDK Demo", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("vpe2-ios 와 동일 옵션 스키마 · Media3/ExoPlayer 네이티브", fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
            }
        }
        items(DemoScenario.entries) { s ->
            Card(modifier = Modifier.fillMaxWidth().clickable { onSelect(s) }) {
                Column(Modifier.padding(14.dp)) {
                    Text(s.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Text(s.desc, fontSize = 12.sp)
                }
            }
        }
    }
}

@UnstableApi
@Composable
fun ScenarioScaffold(scenario: DemoScenario, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "back") }
            Text(scenario.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Box(Modifier.fillMaxSize().padding(8.dp)) {
            when (scenario) {
                DemoScenario.BASIC -> BasicDemo()
                DemoScenario.BASIC_JSON -> BasicJsonDemo()
                DemoScenario.IMPERATIVE -> ImperativeDemo()
                DemoScenario.LIVE -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                    "playlist" to listOf(mapOf("file" to DemoConfig.LIVE))))
                DemoScenario.WATERMARK -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                    "visibleWatermark" to true, "watermarkText" to "NAVER CLOUD",
                    "watermarkConfig" to mapOf("randPosition" to true, "randPositionInterVal" to 3000, "opacity" to 0.6),
                    "playlist" to listOf(mapOf("file" to DemoConfig.HLS))))
                DemoScenario.OTT -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                    "playlist" to listOf(mapOf("file" to DemoConfig.HLS_VOD,
                        "intro" to mapOf("start" to "00:02", "duration" to 8.0),
                        "ageRating" to "15"))))
                DemoScenario.SUBTITLE_VTT -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                    "playlist" to listOf(mapOf("file" to DemoConfig.MP4,
                        "vtt" to listOf(mapOf("id" to "en", "file" to DemoConfig.VTT, "label" to "English"))))))
                DemoScenario.DASH -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                    "playlist" to listOf(mapOf("file" to DemoConfig.DASH))))
                DemoScenario.MP4 -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                    "playlist" to listOf(mapOf("file" to DemoConfig.MP4))))
                DemoScenario.DRM -> DrmDemo()
                DemoScenario.SCREEN_REC -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                    "screenRecordingPrevention" to true,
                    "playlist" to listOf(mapOf("file" to DemoConfig.HLS))))
                DemoScenario.NOW_PLAYING -> SimpleDemo(mapOf("autostart" to true,
                    "enableNowPlayingPlaybackState" to true,
                    "playlist" to listOf(mapOf("file" to DemoConfig.MP4,
                        "description" to mapOf("title" to "Big Buck Bunny", "profile_name" to "Blender")))))
                DemoScenario.OBJECT_FIT -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                    "objectFit" to "cover",
                    "playlist" to listOf(mapOf("file" to DemoConfig.HLS))))
                DemoScenario.REMOTE_API -> RemoteApiNote()
            }
        }
    }
}

/** 패턴 A — 간편 컴포넌트. */
@UnstableApi
@Composable
private fun SimpleDemo(options: Map<String, Any?>) {
    VpePlayer(accessKey = DemoConfig.ACCESS_KEY, options = options, modifier = Modifier.fillMaxWidth())
}

@UnstableApi
@Composable
private fun BasicDemo() = SimpleDemo(mapOf(
    "autostart" to true, "muted" to true, "aspectRatio" to "16:9",
    "playlist" to listOf(mapOf("file" to DemoConfig.HLS,
        "description" to mapOf("title" to "VPE Demo"))),
))

@UnstableApi
@Composable
private fun BasicJsonDemo() {
    com.navercloud.vpe.player.VpePlayer(
        accessKey = DemoConfig.ACCESS_KEY,
        optionsJson = """
            { autostart: true, muted: true, aspectRatio: "16/9",
              // 느슨한 JSON — 따옴표 생략/주석/후행콤마 허용
              playlist: [{ file: "${DemoConfig.HLS}", }], }
        """.trimIndent(),
        modifier = Modifier.fillMaxWidth(),
    )
}

/** 패턴 B — 컨트롤러 직접 보유 + 커스텀 컨트롤. */
@UnstableApi
@Composable
private fun ImperativeDemo() {
    val context = LocalContext.current
    val controller = remember {
        VpePlayerController.fromMap(
            context = context,
            options = mapOf(
                "autostart" to false, "controls" to false, "aspectRatio" to "16:9",
                "playlist" to listOf(mapOf("file" to DemoConfig.HLS)),
            ),
            accessKey = DemoConfig.ACCESS_KEY,
        )
    }
    val state by controller.state.collectAsStateWithLifecycle()

    Column {
        VpePlayerView(controller = controller, showBuiltinControls = false, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { controller.play() }) { Text("재생") }
            Button(onClick = { controller.pause() }) { Text("일시정지") }
            Button(onClick = { controller.seekTo(state.currentTime + 10) }) { Text("+10s") }
        }
        Text(if (state.isPlaying) "재생중 ${state.currentTime.toInt()}s" else "정지", Modifier.padding(top = 8.dp))
    }
}

/** DRM — 실제 동작엔 유효한 라이선스/콘텐츠 필요. 옵션 스키마 예시. */
@UnstableApi
@Composable
private fun DrmDemo() {
    Column {
        SimpleDemo(mapOf(
            "autostart" to true,
            "playlist" to listOf(mapOf(
                "drm" to mapOf("com.widevine.alpha" to mapOf(
                    "src" to DemoConfig.DASH,
                    "licenseUri" to "https://proxy.example/widevine",
                    "licenseRequestHeader" to mapOf("x-drm-token" to "BACKEND_SIGNED_TOKEN"),
                )),
            )),
        ))
        Text("※ Widevine 은 백엔드 서명 토큰/콘텐츠가 있어야 복호화됩니다. 에뮬레이터는 L3 제한.",
            fontSize = 11.sp, modifier = Modifier.padding(8.dp))
    }
}

@Composable
private fun RemoteApiNote() {
    Text(
        "서버에서 옵션 JSON 을 받아 VpePlayer(accessKey, optionsJson = serverJson) 로 전달하세요. " +
            "느슨한 JSON 파싱을 지원합니다.",
        modifier = Modifier.padding(16.dp),
    )
}
