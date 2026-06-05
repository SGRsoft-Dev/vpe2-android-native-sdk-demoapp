package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * DRM(Widevine) 재생 데모.
 *
 * 플레이리스트 항목의 `drm["com.widevine.alpha"]` 에 라이선스 정보를 지정한다:
 * - `src`: 암호화된 콘텐츠(.mpd 등) URL
 * - `licenseUri`: 라이선스 서버 URL
 * - `licenseRequestHeader`: 라이선스 요청 시 전달할 헤더(백엔드 서명 토큰 패스스루)
 */
@UnstableApi
@Composable
fun DrmDemo() = DemoPlayerScaffold(
    note = "※ Widevine 은 백엔드 서명 토큰/콘텐츠가 있어야 복호화됩니다. 에뮬레이터는 L3 제한.",
) { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "playlist" to listOf(
                mapOf(
                    "drm" to mapOf(
                        "com.widevine.alpha" to mapOf(
                            "src" to "https://dash.akamaized.net/akamai/bbb_30fps/bbb_30fps.mpd",
                            "licenseUri" to "https://proxy.example/widevine",
                            "licenseRequestHeader" to mapOf("x-drm-token" to "BACKEND_SIGNED_TOKEN"),
                        ),
                    ),
                ),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
