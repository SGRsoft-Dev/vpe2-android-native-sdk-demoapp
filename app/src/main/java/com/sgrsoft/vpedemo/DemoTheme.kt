package com.sgrsoft.vpedemo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** 데모 앱 다크 테마 팔레트 (iOS DemoTheme 포팅). */
object DemoTheme {
    val appBackground = Color(0xFF121217)
    val cardBackground = Color(0xFF1C1E26)
    val cardBorder = Color.White.copy(alpha = 0.05f)
    val accent = Color(0xFF2563EB)
    val segmentInactive = Color.White.copy(alpha = 0.06f)
    val textPrimary = Color.White
    val textSecondary = Color.White.copy(alpha = 0.55f)
    val textTertiary = Color.White.copy(alpha = 0.40f)
    val fieldBackground = Color.Black.copy(alpha = 0.35f)
}

/** 다크 카드 컨테이너 (헤더 아이콘 + 타이틀). */
@Composable
fun DemoCard(icon: ImageVector, title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DemoTheme.cardBackground, RoundedCornerShape(14.dp))
            .border(1.dp, DemoTheme.cardBorder, RoundedCornerShape(14.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(icon, null, tint = DemoTheme.textPrimary, modifier = Modifier.padding(0.dp))
            Text(title, color = DemoTheme.textPrimary, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
        content()
    }
}

/** 작은 회색 라벨 + 컨트롤. */
@Composable
fun DemoLabeledField(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label.uppercase(), color = DemoTheme.textTertiary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
        content()
    }
}

/** 2-탭 세그먼티드 토글 (pub/gov, real/beta). */
@Composable
fun DemoSegmented(options: List<Pair<String, String>>, selected: String, onSelect: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        options.forEach { (value, label) ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(if (selected == value) DemoTheme.accent else DemoTheme.segmentInactive, RoundedCornerShape(10.dp))
                    .clickable { onSelect(value) }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(label, color = DemoTheme.textPrimary, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

/** 모노스페이스 읽기전용 텍스트 박스 + 액션 버튼. */
@Composable
fun DemoReadonlyWithAction(value: String, actionLabel: String, onAction: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            value, color = DemoTheme.textPrimary, fontSize = 14.sp, fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .weight(1f)
                .background(DemoTheme.fieldBackground, RoundedCornerShape(10.dp))
                .padding(horizontal = 14.dp, vertical = 14.dp),
            maxLines = 1,
        )
        DemoActionButton(actionLabel, onAction)
    }
}

/** 편집 가능한 텍스트 입력 박스 + 액션 버튼. */
@Composable
fun DemoFieldWithAction(state: MutableState<TextFieldValue>, actionLabel: String, onAction: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        BasicTextField(
            value = state.value,
            onValueChange = { state.value = it },
            singleLine = true,
            textStyle = TextStyle(color = DemoTheme.textPrimary, fontSize = 14.sp, fontFamily = FontFamily.Monospace),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(DemoTheme.accent),
            modifier = Modifier
                .weight(1f)
                .background(DemoTheme.fieldBackground, RoundedCornerShape(10.dp))
                .padding(horizontal = 14.dp, vertical = 14.dp),
        )
        DemoActionButton(actionLabel, onAction)
    }
}

@Composable
fun DemoActionButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(DemoTheme.accent, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 22.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, color = DemoTheme.textPrimary, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    }
}
