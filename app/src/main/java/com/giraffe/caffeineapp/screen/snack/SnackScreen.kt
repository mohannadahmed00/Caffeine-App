package com.giraffe.caffeineapp.screen.snack

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.ui.theme.darkGray
import com.giraffe.caffeineapp.ui.theme.urbanist
import com.giraffe.caffeineapp.ui.theme.white
import kotlin.math.abs

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SnackScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToThankYouScreen: (Snack) -> Unit,
    onCloseClick: () -> Unit
) {
    val snacks by rememberSaveable {
        mutableStateOf(
            listOf(
                Snack("chocolate", R.drawable.chocolate),
                Snack("muffin", R.drawable.muffin),
                Snack("cookie", R.drawable.cookie),
                Snack("cinnamon roll", R.drawable.cinnamon_roll),
                Snack("croissant", R.drawable.croissant),
                Snack("oreo", R.drawable.oreo),
            )
        )
    }

    val pagerState = rememberPagerState(0) { snacks.size }

    val screenHeight = LocalWindowInfo.current.containerSize.height.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(28.dp)
                .size(24.dp)
                .align(Alignment.Start)
                .clickable(onClick = onCloseClick),
            imageVector = Icons.Rounded.Close,
            tint = darkGray.copy(.87f),
            contentDescription = stringResource(R.string.close)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp, top = 8.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.take_your_snack),
            style = TextStyle(
                color = darkGray.copy(.87f),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Start
            )
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            VerticalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f),
                state = pagerState,
                pageSpacing = -screenHeight / 4,
                contentPadding = PaddingValues(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            { pageIndex ->
                val isHero = pageIndex == pagerState.currentPage
                val offsetX = animateDpAsState(
                    if (isHero) (-100).dp else {
                        if (abs(pageIndex - pagerState.currentPage) == 1) (-150).dp else (-550).dp
                    }
                ).value
                val offsetZ = animateFloatAsState(
                    if (isHero) 0f
                    else {
                        if (pageIndex > pagerState.currentPage) {
                            30f
                        } else {
                            -30f
                        }
                    }
                ).value
                SnackCard(
                    modifier = Modifier
                        .offset(x = offsetX)
                        .graphicsLayer {
                            rotationZ = offsetZ
                        }
                        .clickable {
                            navigateToThankYouScreen(snacks[pageIndex])
                        },
                    snack = snacks[pageIndex], isIgnored = pagerState.currentPage > pageIndex,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SnackCard(
    modifier: Modifier = Modifier,
    snack: Snack,
    isIgnored: Boolean,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val width = animateDpAsState(if (isIgnored) 200.dp else 260.41.dp)
    val height = animateDpAsState(if (isIgnored) 210.43.dp else 274.dp)
    Box(
        modifier = modifier
            .shadow(50.dp, clip = false)
            .size(width = width.value, height = height.value)
            .background(white, RoundedCornerShape(32.dp))
            .padding(vertical = 63.dp, horizontal = 58.dp)
    ) {
        Image(
            modifier = Modifier.sharedElement(
                sharedContentState = rememberSharedContentState(
                    key = "image-$${snack.image}"
                ),
                animatedVisibilityScope = animatedVisibilityScope,
            ),
            painter = painterResource(snack.image),
            contentDescription = snack.name
        )
    }

}

data class Snack(
    val name: String,
    val image: Int
)