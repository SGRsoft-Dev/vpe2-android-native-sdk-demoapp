#!/usr/bin/env bash
#
# VPE SDK 참조 전환 스크립트 (iOS demoapp/scripts/select-sdk.sh 대응)
# ---------------------------------------------------------------------------
# demoapp 루트의 `.local.env` 유무로 SDK 참조 모드를 전환한다.
#   있으면  → LOCAL  : settings.gradle.kts 가 includeBuild("../sdk") (composite, 소스 직접)
#   없으면  → REMOTE : mavenLocal 의 발행 AAR(com.navercloud.vpe:player) 소비
# `.local.env` 는 .gitignore 됨 → 저장소 기본 상태는 항상 REMOTE.
#
# 사용:
#   scripts/select-sdk.sh           # 현재 상태 출력
#   scripts/select-sdk.sh local     # 로컬(composite) 강제
#   scripts/select-sdk.sh remote    # 바이너리(mavenLocal) 강제
# ---------------------------------------------------------------------------
set -euo pipefail

DEMO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ENV_FILE="$DEMO_DIR/.local.env"
MODE="${1:-status}"

case "$MODE" in
  local)
    cat > "$ENV_FILE" <<'EOF'
# 이 파일이 있으면 demoapp 이 ../sdk 를 composite build 로 직접 참조한다.
# (gitignore 됨 — SDK 개발용 로컬 전용)
VPE_SDK_LOCAL=1
EOF
    echo "▶ SDK 참조 모드 → LOCAL (composite ../sdk)"
    echo "  .local.env 생성됨. (Android Studio: Gradle sync 다시 실행)"
    ;;
  remote)
    rm -f "$ENV_FILE"
    echo "▶ SDK 참조 모드 → REMOTE (mavenLocal com.navercloud.vpe:player)"
    echo "  .local.env 제거됨. 먼저 SDK 를 발행하세요: (cd ../sdk && scripts/build-aar.sh <ver>)"
    ;;
  status)
    if [ -f "$ENV_FILE" ]; then
      echo "현재 모드: LOCAL (.local.env 있음 → composite ../sdk)"
    else
      echo "현재 모드: REMOTE (.local.env 없음 → mavenLocal)"
    fi
    ;;
  *)
    echo "사용법: scripts/select-sdk.sh [local|remote|status]" >&2
    exit 1
    ;;
esac
