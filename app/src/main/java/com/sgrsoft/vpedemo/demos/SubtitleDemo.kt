package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * 자막(VTT) 데모 — 외부 사이드카 자막.
 *
 * 플레이리스트 항목의 `vtt` 배열에 트랙을 지정하면 자막 버튼으로 on/off·언어 선택이 가능하다.
 * 각 트랙: `id`(고유 키) · `file`(.vtt URL) · `label`(표시명) · `default`(기본 선택 여부, 선택).
 * 자막 스타일은 시스템 접근성 자막 환경설정(설정 > 접근성 > 자막)을 따른다.
 */
@UnstableApi
@Composable
fun SubtitleDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        platform = platform,
        stage = stage,
        options = mapOf(
            "autostart" to true,
            "muted" to true,
            "playlist" to listOf(
                mapOf(
                    "file" to "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    "vtt" to listOf(
                        mapOf(
                            "id" to "en",
                            "file" to "https://durian.blender.org/wp-content/content/subtitles/sintel_en.vtt",
                            "label" to "English",
                        ),
                    ),
                ),
            ),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}
