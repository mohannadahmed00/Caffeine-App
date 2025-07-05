package com.giraffe.caffeineapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.ui.theme.darkGray

@Composable
fun ProfileHeaderSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            painter = painterResource(R.drawable.profile),
            contentDescription = stringResource(R.string.profile_image)
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),
            painter = painterResource(R.drawable.add),
            contentDescription = stringResource(R.string.add),
            tint = darkGray.copy(.87f)
        )
    }
}