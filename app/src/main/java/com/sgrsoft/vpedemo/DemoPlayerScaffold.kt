package com.sgrsoft.vpedemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi

/**
 * 모든 데모 공통 스캐폴드 — 상단에 플레이어, 하단에 **Platform/Stage 토글 + AccessKey 입력 + 적용** 패널.
 *
 * 각 데모는 [player] 람다에서 전달받은 (accessKey, platform, stage)로 `VpePlayer(...)` 를 직접 호출하며,
 * **옵션(JSON/Map)은 각 데모 파일에 직접 코딩**한다. "적용"을 누르면 입력값을 반영해 플레이어를 재생성한다.
 *
 * @param note  플레이어 아래 표시할 보조 설명(선택).
 * @param player accessKey/platform/stage 를 받아 플레이어를 그리는 슬롯.
 */
@UnstableApi
@Composable
fun DemoPlayerScaffold(
    note: String? = null,
    player: @Composable (accessKey: String, platform: String, stage: String) -> Unit,
) {
    // 편집 중인 값 — 전역 공유 저장소[DemoSettings]에서 시드(다른 페이지에서 적용한 값이 그대로 보인다).
    var platform by remember { mutableStateOf(DemoSettings.platform) }
    var stage by remember { mutableStateOf(DemoSettings.stage) }
    val licenseKey = remember { mutableStateOf(TextFieldValue(DemoSettings.accessKey)) }
    var reloadToken by remember { mutableIntStateOf(0) }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // 플레이어는 항상 '적용된' 전역 값을 사용 — 한 페이지에서 적용하면 모든 페이지가 동기화된다.
        // 적용 시 key(reloadToken)로 강제 재생성 (VpePlayer 도 accessKey/platform/stage 변경 시 재생성).
        key(reloadToken) { player(DemoSettings.accessKey, DemoSettings.platform, DemoSettings.stage) }

        note?.let {
            Text(it, color = DemoTheme.textTertiary, fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DemoCard(Icons.Filled.Tune, "Configuration") {
                DemoLabeledField("Platform") {
                    DemoSegmented(listOf("pub" to "pub", "gov" to "gov"), platform) { platform = it }
                }
                DemoLabeledField("Stage") {
                    DemoSegmented(listOf("real" to "real", "beta" to "beta"), stage) { stage = it }
                }
                DemoLabeledField("License Key (AccessKey)") {
                    DemoFieldWithAction(licenseKey, "적용") {
                        // 전역 저장소에 저장(로컬 영속) → 모든 데모 페이지 동기화.
                        DemoSettings.update(platform, stage, licenseKey.value.text)
                        reloadToken++
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
