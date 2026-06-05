package com.sgrsoft.vpedemo

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * 데모 공통 Configuration(Platform / Stage / AccessKey) 전역 저장소.
 *
 * 모든 데모 페이지가 이 값을 공유한다 — 한 페이지에서 "적용"하면 다른 페이지에도 즉시 반영되고,
 * SharedPreferences 에 영속되어 앱을 재실행해도 유지된다. (Compose mutableStateOf 라 읽는 화면은 자동 갱신)
 */
object DemoSettings {
    private const val PREFS = "vpe_demo_config"
    private const val K_PLATFORM = "platform"
    private const val K_STAGE = "stage"
    private const val K_ACCESS_KEY = "access_key"

    private var prefs: SharedPreferences? = null

    var platform by mutableStateOf("pub")
        private set
    var stage by mutableStateOf("real")
        private set
    var accessKey by mutableStateOf(DemoConfig.ACCESS_KEY)
        private set

    /** 앱 시작 시 1회 호출 — 영속값 로드(없으면 기본값 유지). */
    fun init(context: Context) {
        if (prefs != null) return
        val p = context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs = p
        platform = p.getString(K_PLATFORM, platform) ?: platform
        stage = p.getString(K_STAGE, stage) ?: stage
        accessKey = p.getString(K_ACCESS_KEY, accessKey) ?: accessKey
    }

    /** "적용" — 값 갱신 + 로컬 영속. 이 값을 읽는 모든 데모 페이지가 동기화된다. */
    fun update(platform: String, stage: String, accessKey: String) {
        this.platform = platform
        this.stage = stage
        this.accessKey = accessKey
        prefs?.edit()
            ?.putString(K_PLATFORM, platform)
            ?.putString(K_STAGE, stage)
            ?.putString(K_ACCESS_KEY, accessKey)
            ?.apply()
    }
}
