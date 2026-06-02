# VPE Android 데모앱

`vpe2-ios` VPEDemo 대응 — 14개 시나리오(Basic/JSON/Imperative/Live/Watermark/OTT/Subtitle/DASH/MP4/DRM/ScreenRec/NowPlaying/ObjectFit/RemoteApi).

## 실행
1. `app/src/main/java/com/sgrsoft/vpedemo/MainActivity.kt` 의 `DemoConfig.ACCESS_KEY` 를 유효 키로 교체.
2. SDK 참조 모드 선택:
   - 로컬(SDK 소스 개발): `scripts/select-sdk.sh local` → `../sdk` composite.
   - 배포 검증(AAR): `(cd ../sdk && scripts/build-aar.sh)` 후 `scripts/select-sdk.sh remote`.
3. `./gradlew :app:installDebug` 또는 Android Studio 실행.

`.local.env` 유무가 모드를 결정(gitignore, 기본 remote).
