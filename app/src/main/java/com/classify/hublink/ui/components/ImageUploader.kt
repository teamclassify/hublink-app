package com.classify.hublink.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.classify.hublink.ui.theme.HublinkTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageUploader(
    onImageUrlReady: (String) -> Unit,
    uploadImageToSupabase: suspend (context: Context, uri: Uri) -> String?,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { uri ->
                scope.launch {
                    isUploading = true
                    imageUrl = uploadImageToSupabase(context, uri)
                    isUploading = false

                    imageUrl?.let { url ->
                        onImageUrlReady(url)
                    }
                }
            }
        }
    )



    Column {
        CustomButton(
            modifier = Modifier.padding(bottom = HublinkTheme.dimens.paddingMedium),
            text = "Select Image",
            textColor = MaterialTheme.colorScheme.primary,
            buttonColor = MaterialTheme.colorScheme.surface,
            onTap = { launcher.launch("image/*") }
        )

        imageUri?.let { uri ->
            GlideImage(
                model = imageUri,
                contentDescription = "Event image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
        }

        if (isUploading) {
            CircularProgressIndicator()
        }
    }
}