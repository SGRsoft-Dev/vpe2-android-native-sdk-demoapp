import java.io.File

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // remote 모드(.local.env 없음): SDK 를 JitPack 에서 소비
        // (com.github.SGRsoft-Dev:vpe2-android-native-sdk)
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "VPEDemo"
include(":app")

// ── SDK 참조 모드 토글 (iOS select-sdk.sh 대응) ─────────────────────────
// `.local.env` 에 `VPE_SDK_PATH=../sdk` 가 있으면 → 그 SDK 경로의 루트 빌드를
//   composite(includeBuild)로 참조(로컬 개발). 없으면 → JitPack 발행본 소비(REMOTE).
//
// SDK 디렉토리(../sdk) 단독은 standalone 빌드가 아니라(루트가 버전 카탈로그+:sdk 모듈 보유)
// 그 부모(루트)를 includeBuild 하고, JitPack 좌표를 :sdk 프로젝트로 명시 치환한다.
// `.local.env` 는 .gitignore 됨 → 저장소 기본 상태는 항상 REMOTE(JitPack).
val localEnv = File(rootDir, ".local.env")
val sdkPath: String? = if (localEnv.exists()) {
    localEnv.readLines()
        .map { it.trim() }
        .firstOrNull { it.startsWith("VPE_SDK_PATH=") }
        ?.substringAfter("=")?.trim()
        ?.takeIf { it.isNotEmpty() }
} else null

if (sdkPath != null) {
    val sdkDir = File(rootDir, sdkPath)
    val rootBuild = sdkDir.parentFile
        ?: error("VPE_SDK_PATH 의 부모(루트 빌드)를 찾을 수 없습니다: $sdkPath")
    println("▶ VPE SDK: LOCAL (composite ${rootBuild.name} → :sdk, VPE_SDK_PATH=$sdkPath)")
    includeBuild(rootBuild) {
        dependencySubstitution {
            substitute(module("com.github.SGRsoft-Dev:vpe2-android-native-sdk")).using(project(":sdk"))
        }
    }
} else {
    println("▶ VPE SDK: REMOTE (JitPack com.github.SGRsoft-Dev:vpe2-android-native-sdk)")
}
