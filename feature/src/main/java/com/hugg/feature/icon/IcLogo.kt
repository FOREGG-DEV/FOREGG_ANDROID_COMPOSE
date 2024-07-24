import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun VectorPreview() {
    Image(Logo, null)
}

private var _Logo: ImageVector? = null

public val Logo: ImageVector
    get() {
        if (_Logo != null) {
            return _Logo!!
        }
        _Logo = ImageVector.Builder(
            name = "Logo",
            defaultWidth = 86.dp,
            defaultHeight = 26.dp,
            viewportWidth = 86f,
            viewportHeight = 26f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF5E5E5E)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(26.5947f, 3.71423f)
                    horizontalLineTo(29.7323f)
                    verticalLineTo(10.338f)
                    horizontalLineTo(39.0256f)
                    verticalLineTo(3.71423f)
                    horizontalLineTo(42.1632f)
                    verticalLineTo(20.738f)
                    horizontalLineTo(39.0256f)
                    verticalLineTo(13.0309f)
                    horizontalLineTo(29.7323f)
                    verticalLineTo(20.738f)
                    horizontalLineTo(26.5947f)
                    verticalLineTo(3.71423f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF5E5E5E)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(43.9263f, 8.10951f)
                    horizontalLineTo(46.8248f)
                    verticalLineTo(15.2905f)
                    curveTo(46.8248f, 15.8167f, 46.8547f, 16.2809f, 46.9443f, 16.6833f)
                    curveTo(47.034f, 17.0857f, 47.1535f, 17.4262f, 47.3627f, 17.7048f)
                    curveTo(47.5719f, 18.0143f, 47.8408f, 18.2309f, 48.2293f, 18.4167f)
                    curveTo(48.6177f, 18.5714f, 49.0958f, 18.6643f, 49.6935f, 18.6643f)
                    curveTo(50.2911f, 18.6643f, 50.4106f, 18.6024f, 50.7991f, 18.4476f)
                    curveTo(51.1876f, 18.3238f, 51.5461f, 18.1071f, 51.9047f, 17.7976f)
                    curveTo(52.2633f, 17.519f, 52.5322f, 17.1167f, 52.7414f, 16.6524f)
                    curveTo(52.9506f, 16.1881f, 53.0701f, 15.6309f, 53.0701f, 14.9809f)
                    verticalLineTo(8.04761f)
                    horizontalLineTo(55.9687f)
                    verticalLineTo(20.6762f)
                    horizontalLineTo(53.0701f)
                    verticalLineTo(18.9119f)
                    horizontalLineTo(53.0402f)
                    curveTo(52.5621f, 19.6238f, 51.9346f, 20.15f, 51.1876f, 20.5524f)
                    curveTo(50.4405f, 20.9548f, 49.6038f, 21.1405f, 48.6476f, 21.1405f)
                    curveTo(47.6914f, 21.1405f, 47.3926f, 21.0786f, 46.8547f, 20.9238f)
                    curveTo(46.3168f, 20.769f, 45.8088f, 20.5214f, 45.3606f, 20.1809f)
                    curveTo(44.9124f, 19.8405f, 44.5837f, 19.3452f, 44.3147f, 18.7262f)
                    curveTo(44.0458f, 18.1071f, 43.9263f, 17.3643f, 43.9263f, 16.4667f)
                    verticalLineTo(8.04761f)
                    verticalLineTo(8.10951f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF5E5E5E)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(71.029f, 8.04761f)
                    verticalLineTo(20.3048f)
                    curveTo(71.029f, 21.1095f, 70.9094f, 21.8214f, 70.6704f, 22.5024f)
                    curveTo(70.4313f, 23.1833f, 70.0429f, 23.7714f, 69.5349f, 24.2976f)
                    curveTo(68.997f, 24.8238f, 68.3396f, 25.2262f, 67.473f, 25.5357f)
                    curveTo(66.6363f, 25.8452f, 65.6203f, 26f, 64.4251f, 26f)
                    curveTo(63.2298f, 26f, 62.6919f, 25.9381f, 61.9449f, 25.8143f)
                    curveTo(61.1978f, 25.6905f, 60.5105f, 25.4738f, 59.9428f, 25.1643f)
                    curveTo(59.375f, 24.8548f, 58.8969f, 24.4214f, 58.5383f, 23.8952f)
                    curveTo(58.1798f, 23.369f, 57.9706f, 22.6881f, 57.9108f, 21.8833f)
                    horizontalLineTo(60.9289f)
                    curveTo(61.0783f, 22.6262f, 61.407f, 23.1214f, 61.9748f, 23.369f)
                    curveTo(62.5425f, 23.6167f, 63.3194f, 23.7405f, 64.3354f, 23.7405f)
                    curveTo(65.3514f, 23.7405f, 65.7698f, 23.6476f, 66.3076f, 23.4309f)
                    curveTo(66.8156f, 23.2143f, 67.2041f, 22.9357f, 67.473f, 22.5952f)
                    curveTo(67.742f, 22.2548f, 67.9212f, 21.8524f, 68.0109f, 21.45f)
                    curveTo(68.1005f, 21.0476f, 68.1603f, 20.5833f, 68.1603f, 20.119f)
                    verticalLineTo(18.6024f)
                    horizontalLineTo(68.1005f)
                    curveTo(67.4431f, 19.3143f, 66.726f, 19.8405f, 66.0088f, 20.15f)
                    curveTo(65.2618f, 20.4595f, 64.455f, 20.6143f, 63.5884f, 20.6143f)
                    curveTo(62.7218f, 20.6143f, 62.0644f, 20.4905f, 61.3472f, 20.2429f)
                    curveTo(60.6002f, 19.9952f, 59.9428f, 19.6238f, 59.3452f, 19.1286f)
                    curveTo(58.7475f, 18.6333f, 58.2694f, 17.9524f, 57.9407f, 17.1476f)
                    curveTo(57.5821f, 16.3429f, 57.4028f, 15.3524f, 57.4028f, 14.2381f)
                    curveTo(57.4028f, 13.1238f, 57.5224f, 12.5976f, 57.7614f, 11.8238f)
                    curveTo(58.0005f, 11.05f, 58.3889f, 10.369f, 58.8969f, 9.75f)
                    curveTo(59.4049f, 9.131f, 60.0623f, 8.6667f, 60.8392f, 8.2952f)
                    curveTo(61.6162f, 7.9548f, 62.5425f, 7.769f, 63.5585f, 7.769f)
                    curveTo(64.5745f, 7.769f, 65.6203f, 7.9548f, 66.4272f, 8.3262f)
                    curveTo(67.234f, 8.6976f, 67.8017f, 9.2238f, 68.1304f, 9.9048f)
                    horizontalLineTo(68.1603f)
                    verticalLineTo(8.04761f)
                    horizontalLineTo(71.0588f)
                    horizontalLineTo(71.029f)
                    close()
                    moveTo(60.6002f, 15.569f)
                    curveTo(60.7496f, 16.0333f, 60.9588f, 16.4357f, 61.2875f, 16.8071f)
                    curveTo(61.6162f, 17.1786f, 62.0345f, 17.4881f, 62.5425f, 17.7048f)
                    curveTo(63.0505f, 17.9214f, 63.678f, 18.0452f, 64.3952f, 18.0452f)
                    curveTo(65.5008f, 18.0452f, 66.3973f, 17.6738f, 67.0846f, 16.9619f)
                    curveTo(67.7718f, 16.219f, 68.1304f, 15.2905f, 68.1304f, 14.1143f)
                    curveTo(68.1304f, 12.9381f, 68.0109f, 12.7833f, 67.742f, 12.2881f)
                    curveTo(67.473f, 11.7929f, 67.1443f, 11.3905f, 66.7559f, 11.1119f)
                    curveTo(66.3375f, 10.8333f, 65.9192f, 10.6167f, 65.4709f, 10.4929f)
                    curveTo(65.0227f, 10.369f, 64.5745f, 10.3071f, 64.1561f, 10.3071f)
                    curveTo(63.0804f, 10.3071f, 62.154f, 10.6786f, 61.4668f, 11.3905f)
                    curveTo(60.7496f, 12.1024f, 60.391f, 13.0309f, 60.391f, 14.1762f)
                    curveTo(60.391f, 15.3214f, 60.4508f, 15.1357f, 60.6002f, 15.6f)
                    verticalLineTo(15.569f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF5E5E5E)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(85.9103f, 8.04761f)
                    verticalLineTo(20.3048f)
                    curveTo(85.9103f, 21.1095f, 85.7908f, 21.8214f, 85.5517f, 22.5024f)
                    curveTo(85.3127f, 23.1833f, 84.9242f, 23.7714f, 84.4162f, 24.2976f)
                    curveTo(83.8783f, 24.8238f, 83.2209f, 25.2262f, 82.3544f, 25.5357f)
                    curveTo(81.5177f, 25.8452f, 80.5017f, 26f, 79.3064f, 26f)
                    curveTo(78.1111f, 26f, 77.5733f, 25.9381f, 76.8262f, 25.8143f)
                    curveTo(76.0792f, 25.6905f, 75.3919f, 25.4738f, 74.8241f, 25.1643f)
                    curveTo(74.2564f, 24.8548f, 73.7783f, 24.4214f, 73.4197f, 23.8952f)
                    curveTo(73.0611f, 23.369f, 72.8519f, 22.6881f, 72.7922f, 21.8833f)
                    horizontalLineTo(75.8102f)
                    curveTo(75.9597f, 22.6262f, 76.2884f, 23.1214f, 76.8561f, 23.369f)
                    curveTo(77.4239f, 23.6167f, 78.2008f, 23.7405f, 79.2168f, 23.7405f)
                    curveTo(80.2328f, 23.7405f, 80.6511f, 23.6476f, 81.189f, 23.4309f)
                    curveTo(81.697f, 23.2143f, 82.0854f, 22.9357f, 82.3544f, 22.5952f)
                    curveTo(82.6233f, 22.2548f, 82.8026f, 21.8524f, 82.8922f, 21.45f)
                    curveTo(82.9819f, 21.0476f, 83.0417f, 20.5833f, 83.0417f, 20.119f)
                    verticalLineTo(18.6024f)
                    horizontalLineTo(82.9819f)
                    curveTo(82.3245f, 19.3143f, 81.6073f, 19.8405f, 80.8902f, 20.15f)
                    curveTo(80.1431f, 20.4595f, 79.3363f, 20.6143f, 78.4697f, 20.6143f)
                    curveTo(77.6032f, 20.6143f, 76.9458f, 20.4905f, 76.2286f, 20.2429f)
                    curveTo(75.4815f, 19.9952f, 74.8241f, 19.6238f, 74.2265f, 19.1286f)
                    curveTo(73.6289f, 18.6333f, 73.1508f, 17.9524f, 72.8221f, 17.1476f)
                    curveTo(72.4635f, 16.3429f, 72.2842f, 15.3524f, 72.2842f, 14.2381f)
                    curveTo(72.2842f, 13.1238f, 72.4037f, 12.5976f, 72.6428f, 11.8238f)
                    curveTo(72.8818f, 11.05f, 73.2703f, 10.369f, 73.7783f, 9.75f)
                    curveTo(74.2863f, 9.131f, 74.9437f, 8.6667f, 75.7206f, 8.2952f)
                    curveTo(76.4975f, 7.9548f, 77.4239f, 7.769f, 78.4398f, 7.769f)
                    curveTo(79.4558f, 7.769f, 80.5017f, 7.9548f, 81.3085f, 8.3262f)
                    curveTo(82.1153f, 8.6976f, 82.6831f, 9.2238f, 83.0118f, 9.9048f)
                    horizontalLineTo(83.0417f)
                    verticalLineTo(8.04761f)
                    horizontalLineTo(85.9402f)
                    horizontalLineTo(85.9103f)
                    close()
                    moveTo(75.4815f, 15.569f)
                    curveTo(75.631f, 16.0333f, 75.8401f, 16.4357f, 76.1688f, 16.8071f)
                    curveTo(76.4975f, 17.1786f, 76.9159f, 17.4881f, 77.4239f, 17.7048f)
                    curveTo(77.9319f, 17.9214f, 78.5594f, 18.0452f, 79.2765f, 18.0452f)
                    curveTo(80.3822f, 18.0452f, 81.2786f, 17.6738f, 81.9659f, 16.9619f)
                    curveTo(82.6532f, 16.219f, 83.0118f, 15.2905f, 83.0118f, 14.1143f)
                    curveTo(83.0118f, 12.9381f, 82.8922f, 12.7833f, 82.6233f, 12.2881f)
                    curveTo(82.3544f, 11.7929f, 82.0257f, 11.3905f, 81.6372f, 11.1119f)
                    curveTo(81.2189f, 10.8333f, 80.8005f, 10.6167f, 80.3523f, 10.4929f)
                    curveTo(79.9041f, 10.369f, 79.4558f, 10.3071f, 79.0375f, 10.3071f)
                    curveTo(77.9617f, 10.3071f, 77.0354f, 10.6786f, 76.3481f, 11.3905f)
                    curveTo(75.631f, 12.1024f, 75.2724f, 13.0309f, 75.2724f, 14.1762f)
                    curveTo(75.2724f, 15.3214f, 75.3321f, 15.1357f, 75.4815f, 15.6f)
                    verticalLineTo(15.569f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFFFFD19B)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(1.43457f, 0.30957f)
                    horizontalLineTo(5.67779f)
                    curveTo(7.4109f, 0.3096f, 8.8154f, 1.7643f, 8.8154f, 3.5596f)
                    verticalLineTo(24.762f)
                    horizontalLineTo(1.43457f)
                    verticalLineTo(0.30957f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF84D1BF)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(17.4512f, 0.30957f)
                    horizontalLineTo(21.6944f)
                    verticalLineTo(24.762f)
                    horizontalLineTo(14.2837f)
                    verticalLineTo(3.55957f)
                    curveTo(14.2837f, 1.7643f, 15.6881f, 0.3096f, 17.4213f, 0.3096f)
                    horizontalLineTo(17.4512f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFFFFD19B)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(22.4712f, 11.8239f)
                    curveTo(22.0229f, 11.2048f, 21.1862f, 10.9263f, 20.499f, 11.2668f)
                    curveTo(18.6762f, 12.1644f, 15.9569f, 12.7525f, 12.9388f, 12.7525f)
                    curveTo(9.9208f, 12.7525f, 7.2015f, 12.1953f, 5.3787f, 11.2668f)
                    curveTo(4.6914f, 10.9263f, 3.9444f, 11.1429f, 3.4364f, 11.8548f)
                    curveTo(3.1675f, 12.2263f, 3.0181f, 12.6287f, 3.0181f, 13.031f)
                    curveTo(3.0181f, 15.3215f, 7.4705f, 17.1787f, 12.9388f, 17.1787f)
                    curveTo(13.9548f, 17.1787f, 14.9708f, 17.1168f, 15.9569f, 16.9929f)
                    curveTo(16.943f, 16.8691f, 17.7498f, 16.6834f, 18.6164f, 16.4358f)
                    curveTo(19.483f, 16.1882f, 20.0806f, 15.9406f, 20.738f, 15.6001f)
                    curveTo(21.3954f, 15.2596f, 21.7839f, 14.981f, 22.2022f, 14.5168f)
                    curveTo(22.7102f, 13.9596f, 22.9792f, 13.1858f, 22.77f, 12.4429f)
                    curveTo(22.7102f, 12.2263f, 22.5907f, 12.0096f, 22.4712f, 11.8239f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF84D1BF)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(20.1106f, 9.03812f)
                    curveTo(20.1106f, 8.6357f, 19.9612f, 8.2333f, 19.6923f, 7.8619f)
                    curveTo(19.1843f, 7.15f, 18.4372f, 6.9643f, 17.7499f, 7.2738f)
                    curveTo(15.9271f, 8.1715f, 13.2079f, 8.7596f, 10.1898f, 8.7596f)
                    curveTo(7.1718f, 8.7596f, 4.4525f, 8.2024f, 2.6297f, 7.2738f)
                    curveTo(1.9424f, 6.9333f, 1.1954f, 7.15f, 0.6874f, 7.8619f)
                    curveTo(0.4185f, 8.2333f, 0.269f, 8.6357f, 0.269f, 9.0381f)
                    curveTo(0.269f, 9.6262f, 0.5679f, 10.1524f, 0.9563f, 10.5548f)
                    curveTo(1.3448f, 10.9572f, 2.1516f, 11.5143f, 2.8389f, 11.8238f)
                    curveTo(3.5262f, 12.1334f, 4.6916f, 12.5357f, 5.6777f, 12.7214f)
                    curveTo(6.6638f, 12.9072f, 8.0084f, 13.0929f, 9.1738f, 13.1548f)
                    curveTo(10.5185f, 13.2167f, 11.8632f, 13.1548f, 13.2079f, 12.9691f)
                    curveTo(14.5526f, 12.7834f, 16.3156f, 12.5357f, 17.3914f, 12.2881f)
                    curveTo(17.2718f, 12.2881f, 16.1662f, 12.35f, 16.0467f, 12.381f)
                    curveTo(16.1662f, 12.381f, 16.3156f, 12.2881f, 16.4351f, 12.2572f)
                    curveTo(16.5547f, 12.2262f, 16.7041f, 12.1643f, 16.8236f, 12.1024f)
                    curveTo(17.0925f, 12.0095f, 17.3615f, 11.8857f, 17.6304f, 11.7619f)
                    curveTo(18.1384f, 11.5143f, 18.6464f, 11.2357f, 19.0647f, 10.8643f)
                    curveTo(19.6026f, 10.4f, 20.1106f, 9.781f, 20.1106f, 9.0072f)
                    verticalLineTo(9.03812f)
                    close()
                }
            }
        }.build()
        return _Logo!!
    }

