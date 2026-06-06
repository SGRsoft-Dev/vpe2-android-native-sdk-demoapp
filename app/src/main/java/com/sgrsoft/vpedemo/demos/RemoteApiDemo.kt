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

// 원격 옵션 API — playlist 포함 옵션 JSON 을 내려준다. (경로 대소문자 주의: playUrl)
private const val PLAYURL_ENDPOINT = "https://vpe.sgrsoft.com/api/playUrl?v=1"

private sealed interface RemoteLoad {
    data object Loading : RemoteLoad
    data class Loaded(val json: String) : RemoteLoad
    data class Failed(val message: String) : RemoteLoad
}

/**
 * 원격 API 데모 — 백엔드에서 받은 옵션 JSON(playlist 포함)으로 재생.
 *
 * [PLAYURL_ENDPOINT] 응답(서버가 내려준 옵션 JSON, playlist/aspectRatio/description 등)을
 * 그대로 `VpePlayer(accessKey, optionsJson)` 에 전달한다. SDK 가 느슨한 JSON 도 파싱한다.
 */
@UnstableApi
@Composable
fun RemoteApiDemo() = DemoPlayerScaffold(
    note = "백엔드($PLAYURL_ENDPOINT)에서 playlist 포함 옵션 JSON 을 받아 그대로 재생합니다.",
) { accessKey, platform, stage ->
    var state by remember { mutableStateOf<RemoteLoad>(RemoteLoad.Loading) }
    var reload by remember { mutableIntStateOf(0) }

    LaunchedEffect(reload) {
        state = RemoteLoad.Loading
        state = fetchRemoteOptions(PLAYURL_ENDPOINT)
    }

    when (val s = state) {
        is RemoteLoad.Loading -> SlotBox {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(20.dp))
                Text("옵션 수신 중…", color = DemoTheme.textSecondary, fontSize = 13.sp)
            }
        }
        is RemoteLoad.Loaded -> VpePlayer(
            accessKey = accessKey,
            optionsJson = s.json,
            platform = platform,
            stage = stage,
            modifier = Modifier.fillMaxWidth(),
        )
        is RemoteLoad.Failed -> SlotBox {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("옵션을 불러오지 못했습니다", color = Color.White, fontSize = 14.sp)
                Text(s.message, color = DemoTheme.textSecondary, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 24.dp))
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

private suspend fun fetchRemoteOptions(endpoint: String): RemoteLoad = withContext(Dispatchers.IO) {
    var conn: HttpURLConnection? = null
    try {
        conn = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            connectTimeout = 15_000
            readTimeout = 15_000
            useCaches = false
            setRequestProperty("Cache-Control", "no-cache")
        }
        val code = conn.responseCode
        if (code !in 200..299) return@withContext RemoteLoad.Failed("HTTP $code")
        val json = conn.inputStream.bufferedReader().use { it.readText() }
        if (json.isBlank()) RemoteLoad.Failed("빈 응답") else RemoteLoad.Loaded(json)
    } catch (e: Exception) {
        RemoteLoad.Failed("네트워크 오류: ${e.message}")
    } finally {
        conn?.disconnect()
    }
}
