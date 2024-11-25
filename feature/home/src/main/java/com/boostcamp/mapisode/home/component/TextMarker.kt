package com.boostcamp.mapisode.home.component

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naver.maps.map.overlay.OverlayImage

fun createTextMarkerBitmap(
	width: Int,
	height: Int,
	backgroundColor: Int,
	borderColor: Int,
	borderWidth: Float,
	cornerRadius: Float,
	text: String,
	textSize: Float,
	textColor: Int,
): Bitmap {
	val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
	val canvas = Canvas(bitmap)
	val paint = Paint(Paint.ANTI_ALIAS_FLAG)

	val halfWidth = width / 2f
	val rectHeight = height - (height / 4f)
	val triangleWidth = width / 4f

	val path = Path().apply {
		// 시작점: 왼쪽 아래 모서리의 시작점 (둥근 부분의 시작)
		moveTo(borderWidth / 2 + cornerRadius, rectHeight)

		// 왼쪽 아래 모서리의 둥근 부분
		val leftBottomRect = RectF(
			borderWidth / 2,
			rectHeight - 2 * cornerRadius,
			borderWidth / 2 + 2 * cornerRadius,
			rectHeight,
		)
		addRoundedCorner(leftBottomRect, 90f, 90f)

		// 왼쪽 면을 따라 위로 이동
		lineTo(borderWidth / 2, borderWidth / 2 + cornerRadius)

		// 왼쪽 위 모서리의 둥근 부분
		val leftTopRect = RectF(
			borderWidth / 2,
			borderWidth / 2,
			borderWidth / 2 + 2 * cornerRadius,
			borderWidth / 2 + 2 * cornerRadius,
		)
		addRoundedCorner(leftTopRect, 180f, 90f)

		// 상단 면을 따라 오른쪽으로 이동
		lineTo(width - borderWidth / 2 - cornerRadius, borderWidth / 2)

		// 오른쪽 위 모서리의 둥근 부분
		val rightTopRect = RectF(
			width - borderWidth / 2 - 2 * cornerRadius,
			borderWidth / 2,
			width - borderWidth / 2,
			borderWidth / 2 + 2 * cornerRadius,
		)
		addRoundedCorner(rightTopRect, 270f, 90f)

		// 오른쪽 면을 따라 아래로 이동
		lineTo(width - borderWidth / 2, rectHeight - cornerRadius)

		// 오른쪽 아래 모서리의 둥근 부분
		val rightBottomRect = RectF(
			width - borderWidth / 2 - 2 * cornerRadius,
			rectHeight - 2 * cornerRadius,
			width - borderWidth / 2,
			rectHeight,
		)
		addRoundedCorner(rightBottomRect, 0f, 90f)

		// 삼각형의 오른쪽 꼭짓점으로 이동
		lineTo(halfWidth + triangleWidth / 2, rectHeight)
		// 삼각형의 아래쪽 끝점 (중앙 아래)
		lineTo(halfWidth, height - borderWidth / 2)
		// 삼각형의 왼쪽 꼭짓점으로 이동
		lineTo(halfWidth - triangleWidth / 2, rectHeight)
		// 시작점으로 돌아와 Path를 닫음
		close()
	}

	with(canvas) {
		// 배경색으로 도형 채우기
		drawPath(
			path,
			paint.apply {
				color = backgroundColor
				style = Paint.Style.FILL
			},
		)

		// 테두리 그리기
		drawPath(
			path,
			paint.apply {
				color = borderColor
				style = Paint.Style.STROKE
				strokeWidth = borderWidth
			},
		)

		// 텍스트 그리기
		paint.apply {
			color = textColor
			style = Paint.Style.FILL
			this.textSize = textSize
			textAlign = Paint.Align.CENTER
		}

		val textY = rectHeight / 2 - (paint.descent() + paint.ascent()) / 2

		drawText(text, halfWidth, textY, paint)
	}

	return bitmap
}

@Composable
fun rememberMarkerImage(
	text: String,
	width: Dp = 100.dp,
	height: Dp = 50.dp,
	backgroundColor: Color = Color.White,
	borderColor: Color = Color.Black,
	borderWidth: Dp = 2.dp,
	cornerRadius: Dp = 8.dp,
	textSize: Dp = 14.dp,
	textColor: Color = Color.Black,
): OverlayImage {
	val density = LocalDensity.current

	val bitmap = remember(text) {
		val widthPx = with(density) { width.toPx().toInt() }
		val heightPx = with(density) { height.toPx().toInt() }
		val borderWidthPx = with(density) { borderWidth.toPx() }
		val cornerRadiusPx = with(density) { cornerRadius.toPx() }
		val textSizePx = with(density) { textSize.toPx() }
		val backgroundColorInt = backgroundColor.toArgb()
		val borderColorInt = borderColor.toArgb()
		val textColorInt = textColor.toArgb()

		createTextMarkerBitmap(
			width = widthPx,
			height = heightPx,
			backgroundColor = backgroundColorInt,
			borderColor = borderColorInt,
			borderWidth = borderWidthPx,
			cornerRadius = cornerRadiusPx,
			text = text,
			textSize = textSizePx,
			textColor = textColorInt,
		)
	}

	DisposableEffect(bitmap) {
		onDispose {
			bitmap.recycle()
		}
	}

	return OverlayImage.fromBitmap(bitmap)
}

fun Path.addRoundedCorner(rectF: RectF, startAngle: Float, sweepAngle: Float) {
	arcTo(rectF, startAngle, sweepAngle, false)
}
