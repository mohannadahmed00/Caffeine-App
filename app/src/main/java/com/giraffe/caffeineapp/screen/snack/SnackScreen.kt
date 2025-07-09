package com.giraffe.caffeineapp.screen.snack

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.ui.theme.CaffeineAppTheme
import com.giraffe.caffeineapp.ui.theme.offWhite
import com.giraffe.caffeineapp.ui.theme.white
import kotlin.math.abs

@Composable
fun SnackScreen(modifier: Modifier = Modifier) {
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

    var centeredItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val listState = rememberLazyListState()

    val itemHeight = 250.dp
    val spacing = 16.dp
    val screenHeight = LocalWindowInfo.current.containerSize.height.dp
    val sidePadding = (screenHeight - itemHeight) / 2


    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect {
                // Find which item is closest to the center
                val layoutInfo = listState.layoutInfo
                val viewportCenter = layoutInfo.viewportEndOffset / 2

                val centeredItem = layoutInfo.visibleItemsInfo.minByOrNull { item ->
                    val itemCenter = item.offset + item.size / 2
                    abs(itemCenter - viewportCenter)
                }

                centeredItem?.let { centeredItemIndex = it.index }
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(-50.dp),
            contentPadding = PaddingValues(vertical = 250.dp)

            ) {
            itemsIndexed(snacks) { index, snack ->

                val center = listState.layoutInfo.viewportEndOffset / 2
                val itemOffset = listState.layoutInfo.visibleItemsInfo
                    .find { it.index == index }
                    ?.let { it.offset + it.size / 2 } ?: 0
                val distanceFromCenterPercent = 1f - (abs(center - itemOffset) / center.toFloat())
                val offsetX = animateDpAsState(
                    if (index != centeredItemIndex) -(250 * (1f - distanceFromCenterPercent)).dp
                    else (-20).dp
                ).value

                val offsetZ = animateFloatAsState(
                    if (index == centeredItemIndex) 0f
                    else {
                        if (index > centeredItemIndex) {
                            30f
                        } else {
                            -30f
                        }
                    }
                ).value

                val offsetY = animateDpAsState(
                    if (index > centeredItemIndex) (-50).dp else 0.dp
                ).value

                SnackCard(
                    modifier = Modifier
                        .offset(x = offsetX, y = offsetY)
                        .graphicsLayer {
                            rotationZ = offsetZ
                        },
                    snack = snack, isIgnored = index < centeredItemIndex
                )
            }
        }
    }
}

@Composable
fun SnackCard(modifier: Modifier = Modifier, snack: Snack, isIgnored: Boolean) {
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
            painter = painterResource(snack.image),
            contentDescription = snack.name
        )
    }

}

data class Snack(
    val name: String,
    val image: Int
)

@Preview
@Composable
private fun Preview() {
    CaffeineAppTheme {
        SnackCard(snack = Snack("muffin", R.drawable.muffin), isIgnored = false)
    }
}