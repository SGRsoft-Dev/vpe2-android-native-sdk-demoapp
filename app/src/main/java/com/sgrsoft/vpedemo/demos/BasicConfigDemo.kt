package com.sgrsoft.vpedemo.demos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.navercloud.vpe.player.VpePlayer
import com.sgrsoft.vpedemo.DemoPlayerScaffold

/**
 * 기본 플레이어 구성 데모 (iOS BasicPlayerView 대응).
 *
 * 선언형 사용 패턴: `VpePlayer(accessKey, optionsJson, platform, stage)` 한 번으로 재생.
 * 옵션을 JSON 문자열([BASIC_OPTIONS_JSON])로 직접 코딩했다(실제 NCP 콘텐츠 플레이리스트 + 한/영 자막 포함).
 * 하단 Configuration 에서 platform/stage/accessKey 를 바꾸고 "적용" 하면 플레이어가 재생성된다.
 */
private const val BASIC_OPTIONS_JSON = """
{
  "playlist": [
    {
      "file": "https://u6dwfh2w5883.edge.naverncp.com/hls/-EQTX8kk3dFTfezHSE0rcg__/vodstation/vod-abr-test/j5IXBfIJ83893893_1080p_,AVC_SD_1Pass_30fps_1,AVC_HD_1Pass_30fps,AVC_FHD_1Pass_30fps,.mp4.smil/master.m3u8",
      "poster": "https://tkmenfxu2702.edge.naverncp.com/profile/202605/25e7fe682c76cbbb31e7e6fc79a653ac.png",
      "description": {
        "created_at": "Wed Jul 13 2022 00:00:00 GMT+0900 (한국 표준시)",
        "profile_image": "https://tkmenfxu2702.edge.naverncp.com/profile/202511/cf38c0603c57faacd99cbe6d967c38b3.png",
        "profile_name": "네이버클라우드",
        "title": "1편 — 네이버클라우드 소개"
      },
      vtt: [
        { id: "ko", file: "https://player.vpe.naverncp.com/vtt/ncp_overview_script_kr_v2.vtt", label: "한국어", default: true },
        { id: "en", file: "https://player.vpe.naverncp.com/vtt/ncp_overview_script_en_v2.vtt", label: "영어" },
      ],
    },
    {
      "file": "https://m4qgahqg2249.edge.naverncp.com/hls/a4oif2oPHP-HlGGWOFm29A__/endpoint/sample/221027_NAVER_Cloud_intro_Long_ver_AVC_,FHD_2Pass_30fps,HD_2Pass_30fps,SD_2Pass_30fps,.mp4.smil/master.m3u8",
      "poster": "https://2ardrvaj2252.edge.naverncp.com/endpoint/sample/221027_NAVER_Cloud_intro_Long_ver_01.jpg",
      "description": {
        "created_at": "Wed Jul 13 2022 00:00:00 GMT+0900 (한국 표준시)",
        "profile_image": "https://tkmenfxu2702.edge.naverncp.com/profile/202511/cf38c0603c57faacd99cbe6d967c38b3.png",
        "profile_name": "네이버클라우드",
        "title": "2편 — 두 번째 에피소드"
      }
    },
  ],
  "autostart": true,
  "autoPause": false,
  "allowsPictureInPicture": true,
  "enableNowPlayingPlaybackState": true,
  "screenRecordingPrevention": false,
}
"""

@UnstableApi
@Composable
fun BasicConfigDemo() = DemoPlayerScaffold { accessKey, platform, stage ->
    VpePlayer(
        accessKey = accessKey,
        optionsJson = BASIC_OPTIONS_JSON,
        platform = platform,
        stage = stage,
        modifier = Modifier.fillMaxWidth(),
    )
}
