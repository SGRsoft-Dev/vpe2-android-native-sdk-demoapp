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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.BrandingWatermark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PictureInPictureAlt
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import com.sgrsoft.vpedemo.demos.BasicConfigDemo
import com.sgrsoft.vpedemo.demos.DashDemo
import com.sgrsoft.vpedemo.demos.DrmDemo
import com.sgrsoft.vpedemo.demos.ImperativeDemo
import com.sgrsoft.vpedemo.demos.LiveDemo
import com.sgrsoft.vpedemo.demos.Mp4Demo
import com.sgrsoft.vpedemo.demos.NowPlayingDemo
import com.sgrsoft.vpedemo.demos.ObjectFitDemo
import com.sgrsoft.vpedemo.demos.OttDemo
import com.sgrsoft.vpedemo.demos.PipDemo
import com.sgrsoft.vpedemo.demos.RemoteApiDemo
import com.sgrsoft.vpedemo.demos.ScreenRecordingDemo
import com.sgrsoft.vpedemo.demos.SrtDemo
import com.sgrsoft.vpedemo.demos.SubtitleDemo
import com.sgrsoft.vpedemo.demos.WatermarkDemo

/**
 * 데모 시나리오 메타데이터(목록 카드용). 실제 예제 코드는 `demos/` 패키지의 시나리오별 파일에 있다.
 * 예) [DemoScenario.WATERMARK] → `demos/WatermarkDemo.kt` 의 [WatermarkDemo].
 */
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
    SUBTITLE_SRT("자막 (SRT)", "외부 사이드카 SRT 자막", androidx.compose.material.icons.Icons.Filled.ClosedCaption),
    DASH("DASH", "ExoPlayer DASH 재생 (iOS 미지원)", androidx.compose.material.icons.Icons.Filled.Stream),
    MP4("MP4", "프로그레시브 MP4", androidx.compose.material.icons.Icons.Filled.Movie),
    DRM("DRM (Widevine)", "헤더 패스스루 DRM 재생", androidx.compose.material.icons.Icons.Filled.Lock),
    SCREEN_REC("ScreenRecordingPrevention", "화면 녹화/캡처 방지 (FLAG_SECURE)", androidx.compose.material.icons.Icons.Filled.VisibilityOff),
    NOW_PLAYING("Now Playing", "MediaSession 메타데이터", androidx.compose.material.icons.Icons.Filled.MusicNote),
    OBJECT_FIT("ObjectFit cover", "비디오 채움 모드", androidx.compose.material.icons.Icons.Filled.AspectRatio),
    PIP("Picture-in-Picture", "홈/백그라운드 시 자동 PiP", androidx.compose.material.icons.Icons.Filled.PictureInPictureAlt),
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

/**
 * 선택한 시나리오의 화면(뒤로가기 헤더 + 예제 본문)을 그린다.
 * 각 분기는 `demos/` 패키지의 해당 예제 컴포저블 하나만 호출한다 — 예제 코드는 그 파일을 보면 된다.
 */
@UnstableApi
@Composable
fun ScenarioScaffold(scenario: DemoScenario, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().background(DemoTheme.appBackground)) {
        // PiP 중엔 앱바 숨김 — 비디오만 보이도록.
        if (!PipUiState.inPip) {
            Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.08f)).clickable { onBack() },
                    contentAlignment = Alignment.Center,
                ) { Icon(androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack, "back", tint = DemoTheme.textPrimary) }
                Spacer(Modifier.size(8.dp))
                Text(scenario.title, color = DemoTheme.textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
        when (scenario) {
            DemoScenario.BASIC -> BasicConfigDemo()
            DemoScenario.IMPERATIVE -> ImperativeDemo()
            DemoScenario.LIVE -> LiveDemo()
            DemoScenario.WATERMARK -> WatermarkDemo()
            DemoScenario.OTT -> OttDemo()
            DemoScenario.SUBTITLE_VTT -> SubtitleDemo()
            DemoScenario.SUBTITLE_SRT -> SrtDemo()
            DemoScenario.DASH -> DashDemo()
            DemoScenario.MP4 -> Mp4Demo()
            DemoScenario.DRM -> DrmDemo()
            DemoScenario.SCREEN_REC -> ScreenRecordingDemo()
            DemoScenario.NOW_PLAYING -> NowPlayingDemo()
            DemoScenario.OBJECT_FIT -> ObjectFitDemo()
            DemoScenario.PIP -> PipDemo()
            DemoScenario.REMOTE_API -> RemoteApiDemo()
        }
    }
}
