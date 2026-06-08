#!/usr/bin/env bash
#
# VPE SDK 참조 전환 스크립트 (iOS demoapp/scripts/select-sdk.sh 대응)
# ---------------------------------------------------------------------------
# demoapp 루트의 `.local.env`(VPE_SDK_PATH) 로 SDK 참조 모드를 전환한다.
#   있으면  → LOCAL  : settings.gradle.kts 가 VPE_SDK_PATH 의 루트를 composite(includeBuild)
#   없으면  → REMOTE : JitPack 발행본(com.github.SGRsoft-Dev.vpe2-android-native-sdk:sdk) 소비
# `.local.env` 는 .gitignore 됨 → 저장소 기본 상태는 항상 REMOTE(JitPack).
#
# 사용:
#   scripts/select-sdk.sh                  # 현재 상태 출력
#   scripts/select-sdk.sh local [경로]     # 로컬(composite). 경로 기본값 ../sdk
#   scripts/select-sdk.sh remote           # JitPack 발행본
# ---------------------------------------------------------------------------
set -euo pipefail

DEMO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ENV_FILE="$DEMO_DIR/.local.env"
MODE="${1:-status}"

case "$MODE" in
  local)
    SDK_PATH="${2:-../sdk}"
    cat > "$ENV_FILE" <<EOF
# 이 파일이 있으면 demoapp 이 아래 SDK 경로의 루트를 composite build 로 직접 참조한다.
# (gitignore 됨 — SDK 개발용 로컬 전용). 값은 demoapp 기준 상대경로.
VPE_SDK_PATH=$SDK_PATH
EOF
    echo "▶ SDK 참조 모드 → LOCAL (composite $SDK_PATH)"
    echo "  .local.env 생성됨. (Android Studio: Gradle sync 다시 실행)"
    ;;
  remote)
    rm -f "$ENV_FILE"
    echo "▶ SDK 참조 모드 → REMOTE (JitPack com.github.SGRsoft-Dev:vpe2-android-native-sdk)"
    echo "  .local.env 제거됨. JitPack 빌드본을 소비합니다(jitpack.io)."
    ;;
  status)
    if [ -f "$ENV_FILE" ]; then
      P="$(grep -E '^VPE_SDK_PATH=' "$ENV_FILE" | head -1 | cut -d= -f2)"
      echo "현재 모드: LOCAL (.local.env: VPE_SDK_PATH=${P:-(미설정)})"
    else
      echo "현재 모드: REMOTE (.local.env 없음 → JitPack)"
    fi
    ;;
  *)
    echo "사용법: scripts/select-sdk.sh [local|remote|status]" >&2
    exit 1
    ;;
esac
