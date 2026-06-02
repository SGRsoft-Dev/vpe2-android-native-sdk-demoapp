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
        // remote 모드(.local.env 없음): SDK 를 mavenLocal 좌표(com.navercloud.vpe:player)로 소비
        mavenLocal()
    }
}

rootProject.name = "VPEDemo"
include(":app")

// ── SDK 참조 모드 토글 (iOS select-sdk.sh 대응) ─────────────────────────
// `.local.env` 가 있으면 → 루트 빌드(..)를 composite(includeBuild)로 참조(로컬 개발).
//   루트 빌드가 버전 카탈로그(gradle/libs.versions.toml)와 :sdk 모듈을 보유하므로
//   sdk 디렉토리 단독이 아니라 루트(..)를 포함해야 한다. includeBuild 가
//   com.navercloud.vpe:player 좌표를 :sdk 프로젝트로 자동 치환(dependency substitution).
// `.local.env` 가 없으면 → mavenLocal 의 발행된 AAR 을 소비(배포 검증).
val localEnv = File(rootDir, ".local.env")
if (localEnv.exists()) {
    println("▶ VPE SDK: LOCAL (composite build .. → :sdk)")
    // 명시적 치환: 의존성 좌표(com.navercloud.vpe:player)를 루트 빌드의 :sdk 프로젝트로 매핑.
    // (자동 치환은 프로젝트명 'sdk' 로 매칭하므로 'player' 좌표와 어긋나 mavenLocal 로 폴백함)
    includeBuild("..") {
        dependencySubstitution {
            substitute(module("com.navercloud.vpe:player")).using(project(":sdk"))
        }
    }
} else {
    println("▶ VPE SDK: REMOTE (mavenLocal com.navercloud.vpe:player)")
}
