package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * 원격 API 데모 — 서버에서 받은 옵션 JSON 으로 재생.
 *
 * 서버가 내려준 옵션 JSON 문자열을 그대로 `VpePlayer(accessKey, optionsJson = serverJson)` 에 전달한다.
 * SDK 의 느슨한 JSON 파서가 따옴표 없는 키 / 트레일링 콤마 등도 허용한다(아래 JSON 의 `autostart:` 처럼).
 * 실제로는 이 문자열을 백엔드 응답으로 교체하면 된다.
 */
private const val SERVER_OPTIONS_JSON = """
{
  autostart: true,
  muted: true,
  playlist: [
    { file: "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4" },
  ],
}
"""

@UnstableApi
@Composable
fun RemoteApiDemo() = DemoPlayerScaffold(
    note = "서버 응답(JSON)을 optionsJson 으로 그대로 전달하는 패턴. 느슨한 JSON 문법 허용.",
) { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        optionsJson = SERVER_OPTIONS_JSON,
        platform = platform,
        stage = stage,
        modifier = Modifier.fillMaxWidth(),
    )
}
