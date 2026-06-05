package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoActionButton
import com.sgrsoft.vpedemo.DemoPlayerScaffold
import com.sgrsoft.vpedemo.DemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

private const val DRM_ENDPOINT = "https://vpe.sgrsoft.com/api/drmTest"

private sealed interface DrmLoad {
    data object Loading : DrmLoad
    data class Loaded(val json: String) : DrmLoad
    data class Failed(val message: String) : DrmLoad
}

/**
 * 원클릭 멀티 DRM 데모 (iOS `DrmTestPlayerView` 대응 / NCP Multi-DRM·PallyCon).
 *
 * 1) 백엔드([DRM_ENDPOINT])에서 DRM 옵션 JSON 을 받는다 — playlist[].drm 에 widevine/playready/fairplay
 *    키시스템과 서명된 라이선스 헤더(x-ncp-apigw-signature-v2, x-drm-token 등)가 들어 있다.
 * 2) 받은 JSON 을 그대로 `VpePlayer(accessKey, optionsJson)` 에 주입 → **Android 는 Widevine(DASH)** 경로로 재생.
 *    SDK 가 라이선스 요청 시 서명 헤더를 그대로 패스스루(클라에 시크릿 불필요).
 *
 * ⚠️ Widevine 보안 레벨: 에뮬레이터는 **L3** 라 이 콘텐츠가 복호화되지 않을 수 있다. **실기기(L1)** 에서 확인.
 */
@UnstableApi
@Composable
fun OneClickDrmDemo() = DemoPlayerScaffold(
    note = "백엔드에서 서명된 DRM 옵션 JSON 을 받아 그대로 재생합니다. Widevine 은 실기기(L1)에서 복호화됩니다.",
) { accessKey, platform, stage ->
    var state by remember { mutableStateOf<DrmLoad>(DrmLoad.Loading) }
    var reload by remember { mutableIntStateOf(0) }

    LaunchedEffect(reload) {
        state = DrmLoad.Loading
        state = fetchDrmOptions(DRM_ENDPOINT)
    }

    when (val s = state) {
        is DrmLoad.Loading -> SlotBox {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(20.dp))
                Text("DRM 옵션 수신 중…", color = DemoTheme.textSecondary, fontSize = 13.sp)
            }
        }
        is DrmLoad.Loaded -> VpePlayer(
            accessKey = accessKey,
            optionsJson = s.json,
            platform = platform,
            stage = stage,
            modifier = Modifier.fillMaxWidth(),
        )
        is DrmLoad.Failed -> SlotBox {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(s.message, color = DemoTheme.textSecondary, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 24.dp))
                Spacer(Modifier.size(2.dp))
                DemoActionButton("다시 시도") { reload++ }
            }
        }
    }
}

/** 플레이어 슬롯과 동일한 16:9 검정 박스(로딩/에러 표시용). */
@Composable
private fun SlotBox(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f).background(Color.Black),
        contentAlignment = Alignment.Center,
    ) { content() }
}

/** 백엔드에서 DRM 옵션 JSON 을 받아온다. 서명/토큰은 매 요청 갱신되므로 캐시 무시. */
private suspend fun fetchDrmOptions(endpoint: String): DrmLoad = withContext(Dispatchers.IO) {
    var conn: HttpURLConnection? = null
    try {
        conn = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            connectTimeout = 15_000
            readTimeout = 15_000
            useCaches = false
            setRequestProperty("Cache-Control", "no-cache")
        }
        val code = conn.responseCode
        if (code !in 200..299) return@withContext DrmLoad.Failed("서버 응답 오류 ($code)")
        val json = conn.inputStream.bufferedReader().use { it.readText() }
        if (json.isBlank()) DrmLoad.Failed("빈 응답") else DrmLoad.Loaded(json)
    } catch (e: Exception) {
        DrmLoad.Failed("네트워크 오류: ${e.message}")
    } finally {
        conn?.disconnect()
    }
}
