package com.example.dailyhugg.preview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.hugg.feature.component.FilledBtn
import com.hugg.feature.theme.Black
import com.hugg.feature.theme.WORD_CONFIRM
import java.io.File
import java.io.FileOutputStream

@Composable
fun ImagePreviewScreen(
    selectedUri: Uri? = null,
    popScreen: (Uri?) -> Unit = {}
) {
    val viewModel: ImagePreviewViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.setUri(selectedUri)
    }

    ImagePreviewContent(
        selectedUri = uiState.selectedUri,
        onClickBtnConfirm = {
            selectedUri?.let { uri ->
                val croppedUri = cropImage(context, uri)
                popScreen(croppedUri)
            }
        }
    )
}

@Composable
fun ImagePreviewContent(
    selectedUri: Uri? = null,
    onClickBtnConfirm: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = selectedUri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        CropOverlay()

        FilledBtn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 80.dp)
                .align(Alignment.BottomCenter),
            text = WORD_CONFIRM,
            onClickBtn = { onClickBtnConfirm() }
        )
    }
}

@Composable
fun CropOverlay(
    backgroundColor: Color = Black.copy(alpha = 0.7f)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(backgroundColor)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(375f / 235f)
                .background(Color.Transparent)
                .clip(RectangleShape)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(backgroundColor)
        )
    }
}

fun cropImage(context: Context, uri: Uri): Uri? {
    val inputStream = context.contentResolver.openInputStream(uri)
    val originalBitmap = BitmapFactory.decodeStream(inputStream)
    inputStream?.close()

    val aspectRatio = 375f / 235f
    val width = originalBitmap.width
    val height = originalBitmap.height
    val cropHeight = (width / aspectRatio).toInt()
    val cropTop = (height - cropHeight) / 2
    val croppedBitmap = Bitmap.createBitmap(originalBitmap, 0, cropTop, width, cropHeight)

    return saveBitmapToUri(context, croppedBitmap)
}

fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val file = File(context.cacheDir, "cropped_image_${System.currentTimeMillis()}.jpg")
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewImgPreview() {
    ImagePreviewContent()
}