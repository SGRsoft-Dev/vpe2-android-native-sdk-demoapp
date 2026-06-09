# VPE Android 데모앱

**VPEPlayer Android SDK**(`com.navercloud.vpe:player`)의 사용 예제 모음입니다.
16개 시나리오로 SDK 의 주요 기능과 통합 패턴을 보여줍니다. SDK 사용법은 배포 저장소
[vpe2-android-native-sdk](https://github.com/SGRsoft-Dev/vpe2-android-native-sdk) 의 README 를 참고하세요.

## 실행

1. **access key 설정** — `app/src/main/java/com/sgrsoft/vpedemo/MainActivity.kt` 의
   `DemoConfig.ACCESS_KEY` 를 유효한 NCP access key 로 교체하거나, 각 데모 화면 하단
   **Configuration** 에서 런타임 입력합니다.
2. **빌드/실행**
   ```bash
   export JAVA_HOME="$(/usr/libexec/java_home -v 17 2>/dev/null || echo "/Applications/Android Studio.app/Contents/jbr/Contents/Home")"
   ./gradlew :app:installDebug
   ```
   또는 Android Studio 에서 실행. (JDK 17 필요)

앱을 실행하면 데모 목록에서 시나리오를 선택해 동작을 확인할 수 있습니다.

## 데모 시나리오

각 데모의 전체 코드는 `app/src/main/java/com/sgrsoft/vpedemo/demos/` 에 있습니다.

| 데모 | 보여주는 기능 | 코드 |
|---|---|---|
| **기본 플레이어 구성 (JSON)** | 옵션 JSON 한 번으로 재생. 실제 NCP 콘텐츠 + App Info / Configuration UI | [`demos/BasicConfigDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/BasicConfigDemo.kt) |
| **Imperative Control** | 컨트롤러를 직접 보유(`VpePlayerController`)하고 커스텀 버튼으로 명령형 제어 | [`demos/ImperativeDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/ImperativeDemo.kt) |
| **라이브 스트림** | 라이브 자동 감지 + LIVE 전용 컨트롤 레이아웃 | [`demos/LiveDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/LiveDemo.kt) |
| **워터마크** | 무작위 위치로 이동하는 워터마크 오버레이 | [`demos/WatermarkDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/WatermarkDemo.kt) |
| **OTT 기능** | 인트로/오프닝/엔딩 스킵 버튼 + 연령등급·콘텐츠 경고 고지 | [`demos/OttDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/OttDemo.kt) |
| **자막 (VTT)** | 외부 사이드카 VTT 자막 (다국어 선택·로컬 영속) | [`demos/SubtitleDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/SubtitleDemo.kt) |
| **자막 (SRT)** | 외부 사이드카 SRT 자막 | [`demos/SrtDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/SrtDemo.kt) |
| **DASH** | ExoPlayer DASH(.mpd) 재생 (iOS 미지원, Android 전용) | [`demos/DashDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/DashDemo.kt) |
| **MP4** | 프로그레시브 MP4 직접 재생 | [`demos/Mp4Demo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/Mp4Demo.kt) |
| **DRM (Widevine)** | 라이선스 헤더 패스스루 방식 Widevine 재생 | [`demos/DrmDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/DrmDemo.kt) |
| **One Click Multi DRM** | 백엔드 통신으로 토큰 수신 → Widevine 재생 (NCP Multi-DRM / PallyCon) | [`demos/OneClickDrmDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/OneClickDrmDemo.kt) |
| **ScreenRecordingPrevention** | 화면 녹화/캡처 방지 (`FLAG_SECURE`) | [`demos/ScreenRecordingDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/ScreenRecordingDemo.kt) |
| **Now Playing** | MediaSession 메타데이터 + 잠금화면 미디어 컨트롤 | [`demos/NowPlayingDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/NowPlayingDemo.kt) |
| **ObjectFit cover** | 비디오 채움 모드(`objectFit`) 비교 | [`demos/ObjectFitDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/ObjectFitDemo.kt) |
| **Picture-in-Picture** | 홈/백그라운드 이탈 시 자동 PiP 진입 | [`demos/PipDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/PipDemo.kt) |
| **원격 API 데모** | 백엔드에서 받은 옵션 JSON(playlist 포함)으로 재생 | [`demos/RemoteApiDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/RemoteApiDemo.kt) |

## 통합 패턴 빠르게 보기

- **간편 Composable** (옵션만 넘기면 끝): [`demos/BasicConfigDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/BasicConfigDemo.kt)
- **컨트롤러 직접 보유 + 커스텀 UI**: [`demos/ImperativeDemo.kt`](app/src/main/java/com/sgrsoft/vpedemo/demos/ImperativeDemo.kt)
- 공통 플레이어 스캐폴드: [`DemoPlayerScaffold.kt`](app/src/main/java/com/sgrsoft/vpedemo/DemoPlayerScaffold.kt) · 데모 목록: [`DemoScreens.kt`](app/src/main/java/com/sgrsoft/vpedemo/DemoScreens.kt)

## 문의

- © NAVER Cloud / SGRsoft
- dev@sgrsoft.com
