package com.sgrsoft.vpedemo

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.BrandingWatermark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.key
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.navercloud.vpe.player.VpePlayerController
import com.navercloud.vpe.player.ui.VpePlayerView

/** 데모 시나리오 (iOS VPEDemo 대응). */
enum class DemoScenario(
    val title: String,
    val desc: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
) {
    BASIC("기본 플레이어 구성 (JSON)", "App Info · Configuration · 실제 NCP 콘텐츠", androidx.compose.material.icons.Icons.Filled.PlayCircle),
    IMPERATIVE("Imperative Control", "컨트롤러 직접 보유 + 커스텀 버튼", androidx.compose.material.icons.Icons.Filled.Tune),
    LIVE("라이브 스트림", "라이브 자동 감지 · LIVE 레이아웃", androidx.compose.material.icons.Icons.Filled.Podcasts),
    WATERMARK("워터마크", "무작위 이동 워터마크", androidx.compose.material.icons.Icons.Filled.BrandingWatermark),
    OTT("OTT 기능", "스킵 버튼 · 연령등급/콘텐츠 경고", androidx.compose.material.icons.Icons.Filled.Tv),
    SUBTITLE_VTT("자막 (VTT)", "외부 사이드카 자막", androidx.compose.material.icons.Icons.Filled.Subtitles),
    DASH("DASH", "ExoPlayer DASH 재생 (iOS 미지원)", androidx.compose.material.icons.Icons.Filled.Stream),
    MP4("MP4", "프로그레시브 MP4", androidx.compose.material.icons.Icons.Filled.Movie),
    DRM("DRM (Widevine)", "헤더 패스스루 DRM 재생", androidx.compose.material.icons.Icons.Filled.Lock),
    SCREEN_REC("ScreenRecordingPrevention", "화면 녹화/캡처 방지 (FLAG_SECURE)", androidx.compose.material.icons.Icons.Filled.VisibilityOff),
    NOW_PLAYING("Now Playing", "MediaSession 메타데이터", androidx.compose.material.icons.Icons.Filled.MusicNote),
    OBJECT_FIT("ObjectFit cover", "비디오 채움 모드", androidx.compose.material.icons.Icons.Filled.AspectRatio),
    REMOTE_API("원격 API 데모", "서버 옵션 JSON 수신 → 재생", androidx.compose.material.icons.Icons.Filled.Public);
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun DemoHome(onSelect: (DemoScenario) -> Unit) {
    Column(Modifier.fillMaxSize().background(DemoTheme.appBackground)) {
        // 앱바
        androidx.compose.material3.TopAppBar(
            title = { Text("VPE Android Demo", color = DemoTheme.textPrimary, fontWeight = FontWeight.Bold) },
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                containerColor = DemoTheme.appBackground,
                titleContentColor = DemoTheme.textPrimary,
            ),
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // 헤더 (iOS: 라벨 + 큰 제목 + 안내)
            item {
                Column(modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)) {
                    Text("VIDEO PLAYER ENHANCEMENT V2", color = DemoTheme.textTertiary, fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("데모 시나리오 선택", color = DemoTheme.textPrimary, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text("아래 시나리오를 탭하여 플레이어를 확인하세요.", color = DemoTheme.textSecondary, fontSize = 13.sp)
                }
            }
            // 섹션 헤더
            item {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)) {
                    Icon(androidx.compose.material.icons.Icons.AutoMirrored.Filled.ListAlt, null,
                        tint = DemoTheme.textPrimary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.size(8.dp))
                    Text("데모 시나리오", color = DemoTheme.textPrimary, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            items(DemoScenario.entries) { s -> ScenarioCard(s) { onSelect(s) } }
        }
    }
}

/** 시나리오 카드 — 좌측 아이콘(둥근 사각형) + 제목/설명 + chevron. */
@Composable
private fun ScenarioCard(s: DemoScenario, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DemoTheme.cardBackground, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(40.dp)
                .background(DemoTheme.accent.copy(alpha = 0.16f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(s.icon, null, tint = Color(0xFF6AA9FF), modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.size(14.dp))
        Column(Modifier.weight(1f)) {
            Text(s.title, color = DemoTheme.textPrimary, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(s.desc, color = DemoTheme.textSecondary, fontSize = 12.sp)
        }
        Icon(androidx.compose.material.icons.Icons.Filled.ChevronRight, null,
            tint = DemoTheme.textTertiary, modifier = Modifier.size(22.dp))
    }
}

@UnstableApi
@Composable
fun ScenarioScaffold(scenario: DemoScenario, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().background(DemoTheme.appBackground)) {
        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.08f)).clickable { onBack() },
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "back", tint = DemoTheme.textPrimary) }
            Spacer(Modifier.size(8.dp))
            Text(scenario.title, color = DemoTheme.textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        when (scenario) {
            DemoScenario.BASIC -> BasicConfigScreen()
            DemoScenario.IMPERATIVE -> Box(Modifier.padding(8.dp)) { ImperativeDemo() }
            DemoScenario.LIVE -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                "playlist" to listOf(mapOf("file" to DemoConfig.LIVE))))
            DemoScenario.WATERMARK -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                "visibleWatermark" to true, "watermarkText" to "NAVER CLOUD",
                "watermarkConfig" to mapOf("randPosition" to true, "randPositionInterVal" to 3000, "opacity" to 0.6),
                "playlist" to listOf(mapOf("file" to DemoConfig.HLS))))
            DemoScenario.OTT -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                "playlist" to listOf(mapOf("file" to DemoConfig.HLS_VOD,
                    "intro" to mapOf("start" to "00:02", "duration" to 8.0), "ageRating" to "15"))))
            DemoScenario.SUBTITLE_VTT -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                "playlist" to listOf(mapOf("file" to DemoConfig.MP4,
                    "vtt" to listOf(mapOf("id" to "en", "file" to DemoConfig.VTT, "label" to "English"))))))
            DemoScenario.DASH -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                "playlist" to listOf(mapOf("file" to DemoConfig.DASH))))
            DemoScenario.MP4 -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                "playlist" to listOf(mapOf("file" to DemoConfig.MP4))))
            DemoScenario.DRM -> Box(Modifier.padding(8.dp)) { DrmDemo() }
            DemoScenario.SCREEN_REC -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                "screenRecordingPrevention" to true, "playlist" to listOf(mapOf("file" to DemoConfig.HLS))))
            DemoScenario.NOW_PLAYING -> SimpleDemo(mapOf("autostart" to true,
                "enableNowPlayingPlaybackState" to true,
                "playlist" to listOf(mapOf("file" to DemoConfig.MP4,
                    "description" to mapOf("title" to "Big Buck Bunny", "profile_name" to "Blender")))))
            DemoScenario.OBJECT_FIT -> SimpleDemo(mapOf("autostart" to true, "muted" to true,
                "objectFit" to "cover", "playlist" to listOf(mapOf("file" to DemoConfig.HLS))))
            DemoScenario.REMOTE_API -> Text(
                "서버에서 옵션 JSON 을 받아 VpePlayer(accessKey, optionsJson = serverJson) 로 전달하세요. 느슨한 JSON 파싱 지원.",
                color = DemoTheme.textSecondary, modifier = Modifier.padding(16.dp))
        }
    }
}

/** "기본 플레이어 구성" — iOS BasicPlayerView 와 동일 (플레이어 + App Info + Configuration). */
@UnstableApi
@Composable
private fun BasicConfigScreen() {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val bundleId = context.packageName

    var platform by remember { mutableStateOf("pub") }
    var stage by remember { mutableStateOf("real") }
    val licenseKey = remember { mutableStateOf(TextFieldValue(DemoConfig.ACCESS_KEY)) }

    var appliedPlatform by remember { mutableStateOf("pub") }
    var appliedStage by remember { mutableStateOf("real") }
    var appliedKey by remember { mutableStateOf(DemoConfig.ACCESS_KEY) }
    var reloadToken by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // web <VpePlayer accessKey= options= /> 와 동일. 적용 시 key(reloadToken)로 재생성.
        Box(Modifier.fillMaxWidth().background(Color.Black)) {
            key(reloadToken) {
                VpePlayer(
                    accessKey = appliedKey,
                    optionsJson = DemoConfig.OPTIONS_JSON,
                    platform = appliedPlatform,
                    stage = appliedStage,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DemoCard(Icons.Filled.Info, "App Info") {
                DemoLabeledField("Bundle Identifier") {
                    DemoReadonlyWithAction(bundleId, "복사") {
                        clipboard.setText(AnnotatedString(bundleId))
                    }
                }
            }
            DemoCard(Icons.Filled.Tune, "Configuration") {
                DemoLabeledField("Platform") {
                    DemoSegmented(listOf("pub" to "pub", "gov" to "gov"), platform) { platform = it }
                }
                DemoLabeledField("Stage") {
                    DemoSegmented(listOf("real" to "real", "beta" to "beta"), stage) { stage = it }
                }
                DemoLabeledField("License Key (AccessKey)") {
                    DemoFieldWithAction(licenseKey, "적용") {
                        appliedKey = licenseKey.value.text
                        appliedPlatform = platform
                        appliedStage = stage
                        reloadToken++
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

/** 패턴 A — 간편 컴포넌트. */
@UnstableApi
@Composable
private fun SimpleDemo(options: Map<String, Any?>) {
    VpePlayer(accessKey = DemoConfig.ACCESS_KEY, options = options, modifier = Modifier.fillMaxWidth().padding(8.dp))
}

/** 패턴 B — 컨트롤러 직접 보유 + 커스텀 컨트롤. */
@UnstableApi
@Composable
private fun ImperativeDemo() {
    val context = LocalContext.current
    val controller = remember {
        VpePlayerController.fromMap(
            context = context,
            options = mapOf("autostart" to false, "controls" to false, "aspectRatio" to "16:9",
                "playlist" to listOf(mapOf("file" to DemoConfig.HLS))),
            accessKey = DemoConfig.ACCESS_KEY,
        )
    }
    val state by controller.state.collectAsStateWithLifecycle()

    Column {
        VpePlayerView(controller = controller, showBuiltinControls = false, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DemoActionButton("재생") { controller.play() }
            DemoActionButton("일시정지") { controller.pause() }
            DemoActionButton("+10s") { controller.seekTo(state.currentTime + 10) }
        }
        Text(if (state.isPlaying) "재생중 ${state.currentTime.toInt()}s" else "정지",
            color = DemoTheme.textSecondary, modifier = Modifier.padding(top = 8.dp))
    }
}

@UnstableApi
@Composable
private fun DrmDemo() {
    Column {
        SimpleDemo(mapOf("autostart" to true,
            "playlist" to listOf(mapOf("drm" to mapOf("com.widevine.alpha" to mapOf(
                "src" to DemoConfig.DASH,
                "licenseUri" to "https://proxy.example/widevine",
                "licenseRequestHeader" to mapOf("x-drm-token" to "BACKEND_SIGNED_TOKEN")))))))
        Text("※ Widevine 은 백엔드 서명 토큰/콘텐츠가 있어야 복호화됩니다. 에뮬레이터는 L3 제한.",
            color = DemoTheme.textTertiary, fontSize = 11.sp, modifier = Modifier.padding(8.dp))
    }
}
