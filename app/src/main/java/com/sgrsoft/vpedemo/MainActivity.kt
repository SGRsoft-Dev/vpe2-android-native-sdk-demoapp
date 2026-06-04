package com.sgrsoft.vpedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
@UnstableApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                Surface(modifier = Modifier.fillMaxSize(), color = DemoTheme.appBackground) {
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

    // 기본 테스트 영상 (NCP VOD ABR HLS)
    const val HLS = "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8"
    const val HLS_VOD = HLS
    const val DASH = "https://dash.akamaized.net/akamai/bbb_30fps/bbb_30fps.mpd"
    const val MP4 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    const val LIVE = "https://test-streams.mux.dev/test_001/stream.m3u8"
    const val VTT = "https://durian.blender.org/wp-content/content/subtitles/sintel_en.vtt"

    /** "기본 플레이어 구성" — iOS BasicPlayerView 와 동일한 NCP 실제 콘텐츠 JSON. */
    val OPTIONS_JSON: String = """
    {
      "playlist": [
        {
          "file": "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8",
          "poster": "https://tkmenfxu2702.edge.naverncp.com/profile/202605/25e7fe682c76cbbb31e7e6fc79a653ac.png",
          "description": {
            "created_at": "Wed Jul 13 2022 00:00:00 GMT+0900 (한국 표준시)",
            "profile_image": "https://tkmenfxu2702.edge.naverncp.com/profile/202511/cf38c0603c57faacd99cbe6d967c38b3.png",
            "profile_name": "네이버클라우드",
            "title": "1편 — 네이버클라우드 소개"
          },
          vtt: [
            { id: "ko", file: "https://player.vpe.naverncp.com/vtt/ncp_overview_script_kr_v2.vtt", label: "한국어", default: true },
            { id: "en", file: "https://player.vpe.naverncp.com/vtt/ncp_overview_script_en_v2.vtt", label: "영어" },
          ],
        },
        {
          "file": "https://m4qgahqg2249.edge.naverncp.com/hls/a4oif2oPHP-HlGGWOFm29A__/endpoint/sample/221027_NAVER_Cloud_intro_Long_ver_AVC_,FHD_2Pass_30fps,HD_2Pass_30fps,SD_2Pass_30fps,.mp4.smil/master.m3u8",
          "poster": "https://2ardrvaj2252.edge.naverncp.com/endpoint/sample/221027_NAVER_Cloud_intro_Long_ver_01.jpg",
          "description": {
            "created_at": "Wed Jul 13 2022 00:00:00 GMT+0900 (한국 표준시)",
            "profile_image": "https://tkmenfxu2702.edge.naverncp.com/profile/202511/cf38c0603c57faacd99cbe6d967c38b3.png",
            "profile_name": "네이버클라우드",
            "title": "2편 — 두 번째 에피소드"
          }
        },
      ],
      "autostart": true,
      "autoPause": false,
      "allowsPictureInPicture": true,
      "enableNowPlayingPlaybackState": true,
      "screenRecordingPrevention": false,
    }
    """.trimIndent()
}
