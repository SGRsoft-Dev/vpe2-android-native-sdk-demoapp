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
    note = "PallyCon Widevine 데모 콘텐츠(Big Buck Bunny). 라이선스 요청 헤더(pallycon-customdata-v2)를 패스스루. 에뮬레이터는 L3 제한.",
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
                            "src" to "https://contents.pallycon.com/bunny/stream.mpd",
                            "licenseUri" to "https://license-global.pallycon.com/ri/licenseManager.do",
                            "licenseRequestHeader" to mapOf(
                                "pallycon-customdata-v2" to "eyJrZXlfcm90YXRpb24iOmZhbHNlLCJyZXNwb25zZV9mb3JtYXQiOiJvcmlnaW5hbCIsInVzZXJfaWQiOiJ0ZXN0LXVzZXIiLCJkcm1fdHlwZSI6IldpZGV2aW5lIiwic2l0ZV9pZCI6IkRFTU8iLCJoYXNoIjoiRFNEQ0JwWmhJYVR5VG1MMzlCXC9Yb2IyNzRobWpWXC9oWEp4T1V0K29hZ1pjPSIsImNpZCI6ImJpZ2J1Y2tidW5ueSIsInBvbGljeSI6Im41eDI4dVltRGRQQ0ZpbW9NM25HTnc9PSIsInRpbWVzdGFtcCI6IjIwMjEtMDEtMDZUMDk6MjI6MzZaIn0=",
                            ),
                        ),
                    ),
                ),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
