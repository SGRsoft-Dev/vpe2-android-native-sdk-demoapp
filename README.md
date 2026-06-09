# VPE Android 데모앱

`vpe2-ios` VPEDemo 대응 — 시나리오별 데모(Basic/JSON·Imperative·Live·Watermark·OTT·Subtitle(VTT/SRT)·DASH·MP4·DRM·ScreenRec·NowPlaying·ObjectFit·PiP·RemoteApi).
SDK 소비 방식(로컬 소스 ↔ 배포 AAR)을 토글하며 동작을 검증한다.

## 실행

1. `app/src/main/java/com/sgrsoft/vpedemo/MainActivity.kt` 의 `DemoConfig.ACCESS_KEY` 를 유효한 NCP access key 로 교체
   (또는 데모 화면 하단 Configuration 에서 런타임 입력).
2. SDK 참조 모드 선택 — 아래 `scripts/select-sdk.sh`.
3. `./gradlew :app:installDebug` 또는 Android Studio 에서 실행.

```bash
# JDK 17 필요
export JAVA_HOME="$(/usr/libexec/java_home -v 17 2>/dev/null || echo "/Applications/Android Studio.app/Contents/jbr/Contents/Home")"
```

## SDK 참조 모드 (`scripts/select-sdk.sh`)

`.local.env`(gitignore) 로 전환한다. 파일이 **없으면 기본 REMOTE**.

| 모드 | 조건 | 소비 방식 |
|---|---|---|
| **LOCAL** | `.local.env` 에 `VPE_SDK_PATH=../sdk` | 해당 경로의 루트를 composite(`includeBuild`) — SDK 소스 직접, 빠른 반복 개발 |
| **REMOTE** | `.local.env` 없음 | AAR 전용 배포 저장소(raw GitHub Maven)에서 `com.navercloud.vpe:player` 소비 |

```bash
scripts/select-sdk.sh status            # 현재 모드 출력
scripts/select-sdk.sh local             # LOCAL (기본 경로 ../sdk)
scripts/select-sdk.sh local ../other-sdk  # LOCAL (경로 지정)
scripts/select-sdk.sh remote            # REMOTE (배포 AAR)
```

REMOTE 가 소비하는 저장소(소스 없이 aar 만 — `settings.gradle.kts` 에 설정됨):
```kotlin
maven { url = uri("https://raw.githubusercontent.com/SGRsoft-Dev/vpe2-android-native-sdk/master") }
implementation("com.navercloud.vpe:player:2.0.1")
```

LOCAL 모드에서는 `settings.gradle.kts` 가 위 좌표를 `:sdk` 프로젝트로 dependency-substitution 하므로,
앱 코드(`libs.vpe.player`)는 그대로 두고 모드만 토글하면 된다.

## 참고

- 데모는 별도 Gradle 빌드(루트 SDK 빌드와 분리). `.local.env` 는 gitignore 되어 저장소 기본 상태는 항상 REMOTE.
- SDK 자체 사용법은 배포 저장소 [vpe2-android-native-sdk](https://github.com/SGRsoft-Dev/vpe2-android-native-sdk) 의 README 참고.
