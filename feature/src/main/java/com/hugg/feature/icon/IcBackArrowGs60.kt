package com.hugg.feature.icon

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun VectorPreview() {
    Image(ArrowBackGs60, null)
}

private var _ArrowBackGs60: ImageVector? = null

public val ArrowBackGs60: ImageVector
		get() {
			if (_ArrowBackGs60 != null) {
				return _ArrowBackGs60!!
			}
_ArrowBackGs60 = ImageVector.Builder(
                name = "ArrowBackGs60",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
				path(
    				fill = SolidColor(Color(0xFF808080)),
    				fillAlpha = 1.0f,
    				stroke = null,
    				strokeAlpha = 1.0f,
    				strokeLineWidth = 1.0f,
    				strokeLineCap = StrokeCap.Butt,
    				strokeLineJoin = StrokeJoin.Miter,
    				strokeLineMiter = 1.0f,
    				pathFillType = PathFillType.NonZero
				) {
					moveTo(9.5502f, 12f)
					lineTo(16.9002f, 19.35f)
					curveTo(17.1502f, 19.6f, 17.271f, 19.8917f, 17.2627f, 20.225f)
					curveTo(17.2544f, 20.5583f, 17.1252f, 20.85f, 16.8752f, 21.1f)
					curveTo(16.6252f, 21.35f, 16.3335f, 21.475f, 16.0002f, 21.475f)
					curveTo(15.6669f, 21.475f, 15.3752f, 21.35f, 15.1252f, 21.1f)
					lineTo(7.4252f, 13.425f)
					curveTo(7.2252f, 13.225f, 7.0752f, 13f, 6.9752f, 12.75f)
					curveTo(6.8752f, 12.5f, 6.8252f, 12.25f, 6.8252f, 12f)
					curveTo(6.8252f, 11.75f, 6.8752f, 11.5f, 6.9752f, 11.25f)
					curveTo(7.0752f, 11f, 7.2252f, 10.775f, 7.4252f, 10.575f)
					lineTo(15.1252f, 2.87499f)
					curveTo(15.3752f, 2.625f, 15.671f, 2.5042f, 16.0127f, 2.5125f)
					curveTo(16.3544f, 2.5208f, 16.6502f, 2.65f, 16.9002f, 2.9f)
					curveTo(17.1502f, 3.15f, 17.2752f, 3.4417f, 17.2752f, 3.775f)
					curveTo(17.2752f, 4.1083f, 17.1502f, 4.4f, 16.9002f, 4.65f)
					lineTo(9.5502f, 12f)
					close()
}
}.build()
return _ArrowBackGs60!!
		}

