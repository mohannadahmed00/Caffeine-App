package com.giraffe.caffeineapp.screen.ready

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.composable.DefaultButton
import com.giraffe.caffeineapp.ui.theme.CaffeineAppTheme
import com.giraffe.caffeineapp.ui.theme.brown
import com.giraffe.caffeineapp.ui.theme.darkGray
import com.giraffe.caffeineapp.ui.theme.offWhite
import com.giraffe.caffeineapp.ui.theme.urbanist

@Composable
fun ReadyScreen(navigateToSnackScreen: () -> Unit) {
    var toggle by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .padding(28.dp)
                .size(24.dp)
                .align(Alignment.Start),
            imageVector = Icons.Rounded.Close,
            tint = darkGray.copy(.87f),
            contentDescription = stringResource(R.string.close)
        )
        Box(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .size(56.dp)
                .background(brown, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                imageVector = Icons.Rounded.Check,
                tint = Color.White,
                contentDescription = stringResource(R.string.close)
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 70.dp)
                .padding(bottom = 27.dp),
            text = stringResource(R.string.your_coffee_is_ready_enjoy),
            style = TextStyle(
                color = darkGray.copy(.87f),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Center
            )
        )
        Cup(
            modifier = Modifier
                .padding(bottom = 47.dp)
        )
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToggleButton(isOff = toggle) { toggle = !toggle }
            Text(
                text = stringResource(R.string.take_away), style = TextStyle(
                    color = darkGray.copy(.7f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.25.sp,
                    fontFamily = urbanist,
                    textAlign = TextAlign.Center
                )
            )
        }
        DefaultButton(
            modifier = Modifier
                .padding(bottom = 50.dp),
            text = stringResource(R.string.take_snack),
            painter = painterResource(R.drawable.right_arrow),
            onClick = navigateToSnackScreen
        )
    }
}

@Composable
private fun Cup(modifier: Modifier = Modifier) {
    var cupSize by remember { mutableStateOf(IntSize.Zero) }

    val screenHeight = LocalWindowInfo.current.containerSize.height.dp
    val coverOffsetY = remember {
        androidx.compose.animation.core.Animatable(
            -screenHeight,
            Dp.VectorConverter
        )
    }
    val cupOffsetY = remember {
        androidx.compose.animation.core.Animatable(
            screenHeight,
            Dp.VectorConverter
        )
    }
    LaunchedEffect(Unit) {
        cupOffsetY.animateTo(0.dp,animationSpec = tween(1000))
        coverOffsetY.animateTo((-cupSize.height * .03).dp,animationSpec = tween(1000))
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset(y = cupOffsetY.value),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 58.dp)
                    .fillMaxWidth()
                    .onSizeChanged {
                        cupSize = it
                    },
                painter = painterResource(R.drawable.empty_cup),
                contentDescription = stringResource(R.string.cup),
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier
                    .size(64.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.logo),
                contentScale = ContentScale.Crop
            )
        }
        Image(
            modifier = Modifier
                .padding(horizontal = 52.dp)
                .width(cupSize.width.dp)
                .align(Alignment.TopCenter)
                .offset(y = coverOffsetY.value),
            painter = painterResource(R.drawable.cup_cover),
            contentDescription = stringResource(R.string.cup_cover),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ToggleButton(
    modifier: Modifier = Modifier,
    isOff: Boolean = true,
    onClick: () -> Unit = {}
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val backgroundColor = animateColorAsState(if (isOff) offWhite else brown)
    val offsetX = animateDpAsState(if (!isOff) 0.dp else (size.width / 3.3).dp)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(onClick = onClick)
            .onSizeChanged { newSize ->
                size = newSize // width and height in pixels
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .background(backgroundColor.value)
                .padding(horizontal = 17.dp),
            horizontalArrangement = Arrangement.spacedBy(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "OFF", style = TextStyle(
                    color = Color.Black.copy(.6f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.25.sp,
                    fontFamily = urbanist,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "ON", style = TextStyle(
                    color = Color.White.copy(.6f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.25.sp,
                    fontFamily = urbanist,
                    textAlign = TextAlign.Center
                )
            )
        }
        Image(
            modifier = Modifier
                .size(40.dp)
                .offset(x = offsetX.value)
                .align(Alignment.CenterStart),
            painter = painterResource(R.drawable.foam),
            contentDescription = stringResource(R.string.foam_toggle)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CaffeineAppTheme {
        ReadyScreen {}
    }
}