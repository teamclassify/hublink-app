package com.classify.hublink.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.classify.hublink.data.entities.Event
import com.classify.hublink.ui.theme.HublinkTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventItem(
    event: Event,
    onClick: (event: Event) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = HublinkTheme.dimens.paddingMedium)
            .clickable {
                onClick(event)
            },
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
                    .padding(bottom = HublinkTheme.dimens.paddingMedium, end = HublinkTheme.dimens.paddingMedium),
            ) {
                GlideImage(
                    model = event.image ?: "https://www.ecfmg.org/journeysinmedicine/wp-content/uploads/2021/09/IMGevent3_91f3fsmalledit-1024x719.jpeg", // Replace with your image URL or resource ID
                    contentDescription = event.description,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().border(
                        width = 1.dp,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(16.dp)
                    ),
                    requestBuilderTransform = {
                        it.apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                    }
                )
            }

            Column {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                )

                event.location?.let { Text(text = it) }
            }
        }
    }
}