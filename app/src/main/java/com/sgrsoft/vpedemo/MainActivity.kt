package com.sgrsoft.vpedemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi

/**
 * VPE Android 데모 — iOS VPEDemo 시나리오 대응. **다크모드 고정.**
 * 간단 state 기반 네비게이션(외부 nav 의존 없음).
 */
/** PiP 모드 여부(전역). MainActivity 가 갱신하고, 스캐폴드가 읽어 PiP 중엔 플레이어만 남긴다. */
object PipUiState {
    var inPip by mutableStateOf(false)
}

@UnstableApi
class MainActivity : ComponentActivity() {
    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        // PiP 진입/이탈 → 데모 크롬(앱바/설정) 표시 토글.
        PipUiState.inPip = isInPictureInPictureMode
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DemoSettings.init(this)   // 영속된 Configuration(platform/stage/accessKey) 로드
        // Android 13+: Now Playing 미디어 알림(잠금화면 컨트롤) 표시에 필요한 알림 권한 요청.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
        }
        setContent {
            // 시스템 설정과 무관하게 항상 다크 테마.
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = DemoTheme.appBackground,
                    surface = DemoTheme.appBackground,
                    primary = DemoTheme.accent,
                    onBackground = DemoTheme.textPrimary,
                    onSurface = DemoTheme.textPrimary,
                ),
            ) {
                // Surface(다크 배경)는 시스템 바 뒤까지 채우고(edge-to-edge), 실제 콘텐츠는
                // safeDrawingPadding 으로 상태바/내비바/컷아웃을 피해 안전영역 안에만 그린다.
                Surface(modifier = Modifier.fillMaxSize(), color = DemoTheme.appBackground) {
                    // PiP 중엔 비디오가 창을 가득 채우도록 안전영역 패딩 제거.
                    val safe = if (PipUiState.inPip) Modifier else Modifier.safeDrawingPadding()
                    Box(Modifier.fillMaxSize().then(safe)) {
                        var current by remember { mutableStateOf<DemoScenario?>(null) }
                        val scenario = current
                        if (scenario == null) {
                            DemoHome(onSelect = { current = it })
                        } else {
                            // 데모 화면에서 시스템 back → 앱 종료 대신 메인(목록)으로 복귀.
                            // (전체화면 중이면 Dialog 가 back 을 먼저 소비해 전체화면만 종료)
                            BackHandler { current = null }
                            ScenarioScaffold(scenario = scenario, onBack = { current = null })
                        }
                    }
                }
            }
        }
    }
}

/**
 * 데모 공통 설정.
 *
 * 영상 URL·플레이어 옵션은 각 예제 파일(`demos` 패키지의 *Demo.kt)에 **직접 코딩**되어 있다 —
 * 개발자가 한 파일만 봐도 사용법을 알 수 있도록. 여기에는 모든 데모가 공유하는 데모용 AccessKey 만 둔다.
 */
object DemoConfig {
    // 데모 전용 access key (도메인 com.sgrsoft.vpedemo 잠금) — 바로 실행 가능. 각 데모 하단 입력란 기본값.
    const val ACCESS_KEY = "44fcf7432b280107d7d18148ac24dd99"
}
