package com.sgrsoft.vpedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi

/**
 * VPE Android 데모 — iOS VPEDemo 시나리오 대응.
 * 간단 state 기반 네비게이션(외부 nav 의존 없음).
 *
 * ⚠️ 실제 재생/라이선스 검증은 유효한 accessKey 가 필요합니다. [DemoConfig.ACCESS_KEY] 교체.
 */
@UnstableApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var current by remember { mutableStateOf<DemoScenario?>(null) }
                    val scenario = current
                    if (scenario == null) {
                        DemoHome(onSelect = { current = it })
                    } else {
                        ScenarioScaffold(scenario = scenario, onBack = { current = null })
                    }
                }
            }
        }
    }
}

/** 데모 공통 설정. */
object DemoConfig {
    // 데모 전용 access key (도메인 com.sgrsoft.vpedemo 잠금) — 바로 실행 가능.
    const val ACCESS_KEY = "44fcf7432b280107d7d18148ac24dd99"
    const val HLS = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
    const val HLS_VOD = "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8"
    const val DASH = "https://dash.akamaized.net/akamai/bbb_30fps/bbb_30fps.mpd"
    const val MP4 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    const val LIVE = "https://test-streams.mux.dev/test_001/stream.m3u8"
    const val VTT = "https://durian.blender.org/wp-content/content/subtitles/sintel_en.vtt"
}
