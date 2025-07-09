package com.giraffe.caffeineapp.screen.cupsize

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.composable.DefaultButton
import com.giraffe.caffeineapp.ui.theme.brown
import com.giraffe.caffeineapp.ui.theme.darkGray
import com.giraffe.caffeineapp.ui.theme.sniglet
import com.giraffe.caffeineapp.ui.theme.urbanist
import com.giraffe.caffeineapp.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CupSizeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    coffeeName: String,
    viewModel: CupSizeViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    navigateToReadyScreen: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    Content(state, viewModel, onBackClick, navigateToReadyScreen,animatedVisibilityScope,coffeeName)
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.Content(
    state: CoffeeSizeScreenState,
    interaction: CupSizeScreenInteraction,
    onBackClick: () -> Unit,
    navigateToReadyScreen: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    coffeeName: String,
) {
    LaunchedEffect(state.isCoffeeReady) {
        if (state.isCoffeeReady) navigateToReadyScreen()
    }
    val offsetX = LocalWindowInfo.current.containerSize.width.dp
    val infiniteTransition = rememberInfiniteTransition()
    val loadingOffsetX by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = offsetX,
        animationSpec =
            InfiniteRepeatableSpec(
                animation = tween(3000),
                repeatMode = RepeatMode.Reverse
            ),
        typeConverter = Dp.VectorConverter,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(1f)) {
            if (!state.isCoffeePrepare) {
                Header(
                    modifier = Modifier.fillMaxWidth(),
                    onBackClick = onBackClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                    coffeeName = coffeeName,
                )
            }
        }
        CupSection(
            modifier = Modifier
                .fillMaxWidth(),
            cupSize = state.selectedSize,
            selectedPercentage = state.selectedPercentage,
        )
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!state.isCoffeePrepare) {
                SizeSelector(
                    modifier = Modifier,
                    selectedSize = state.selectedSize,
                    selectSize = interaction::selectSize
                )
                PercentageSelector(
                    modifier = Modifier.padding(top = 16.dp, bottom = 92.dp),
                    selectedPercentage = state.selectedPercentage,
                    selectPercentage = interaction::selectPercentage
                )
                DefaultButton(
                    modifier = Modifier
                        .padding(bottom = 50.dp),
                    text = stringResource(R.string.continue_txt),
                    painter = painterResource(R.drawable.right_arrow),
                    onClick = interaction::prepareCoffee
                )
            } else {
                LoadingSection(offsetX = loadingOffsetX)
                AlmostSection(modifier = Modifier.padding(bottom = 50.dp))
            }
        }
    }
}

@Composable
private fun LoadingSection(modifier: Modifier = Modifier, offsetX: Dp) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(R.drawable.loading_line),
            contentDescription = stringResource(R.string.loading),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .offset(x = offsetX)
                .background(Color.White)
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.Header(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    coffeeName: String,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp)
                .clickable(onClick = onBackClick),
            painter = painterResource(R.drawable.arrow),
            contentDescription = stringResource(R.string.back_arrow),
            tint = darkGray.copy(.87f)
        )
        Text(
            modifier = Modifier
                .sharedElement(
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedContentState = rememberSharedContentState(
                        key = "text-$coffeeName"
                    ),
                ),
            text = coffeeName,
            style = TextStyle(
                color = darkGray.copy(.87f),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun CupSection(
    modifier: Modifier = Modifier,
    cupSize: CupSize = CupSize.MEDIUM,
    selectedPercentage: CoffeePercentage = CoffeePercentage.MEDIUM,
) {
    val startPosition = 10.dp
    val endPosition = -LocalWindowInfo.current.containerSize.height.dp
    val scale = animateFloatAsState(
        when (cupSize) {
            CupSize.SMALL -> .8f
            CupSize.MEDIUM -> .9f
            CupSize.LARGE -> 1f
        }
    )
    val coffeeOffsetY = remember { Animatable(startPosition, Dp.VectorConverter) }
    var shotsCount by rememberSaveable { mutableIntStateOf(0) }
    LaunchedEffect(selectedPercentage) {
        when (selectedPercentage) {
            CoffeePercentage.LOW -> {
                if (shotsCount > 1) {
                    while (shotsCount > 1) {
                        coffeeOffsetY.snapTo(startPosition)
                        coffeeOffsetY.animateTo(endPosition, animationSpec = tween(600))
                        shotsCount--
                    }
                } else {
                    while (shotsCount < 1) {
                        coffeeOffsetY.snapTo(endPosition)
                        coffeeOffsetY.animateTo(startPosition, animationSpec = tween(600))
                        shotsCount++
                    }
                }
            }

            CoffeePercentage.MEDIUM -> {
                if (shotsCount > 2) {
                    while (shotsCount > 2) {
                        coffeeOffsetY.snapTo(startPosition)
                        coffeeOffsetY.animateTo(endPosition, animationSpec = tween(600))
                        shotsCount--
                    }

                } else {
                    while (shotsCount < 2) {
                        coffeeOffsetY.snapTo(endPosition)
                        coffeeOffsetY.animateTo(startPosition, animationSpec = tween(600))
                        shotsCount++
                    }
                }
            }

            CoffeePercentage.HIGH -> {
                while (shotsCount < 3) {
                    coffeeOffsetY.snapTo(endPosition)
                    coffeeOffsetY.animateTo(startPosition, animationSpec = tween(600))
                    shotsCount++
                }
            }
        }
    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier
                .padding(vertical = 63.dp)
                .padding(start = 16.dp)
                .align(Alignment.TopStart),
            text = stringResource(R.string.ml, cupSize.amount),
            style = TextStyle(
                color = darkGray.copy(.87f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Center
            )
        )
        Image(
            modifier = Modifier
                .scale(scale.value / 1.1f)
                .offset(y = coffeeOffsetY.value)
                .align(Alignment.TopCenter),
            painter = painterResource(R.drawable.coffee_beans),
            contentDescription = stringResource(R.string.coffee_beans),
            contentScale = ContentScale.Crop
        )
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .scale(scale.value),
            painter = painterResource(R.drawable.empty_cup),
            contentDescription = stringResource(R.string.cup),
            contentScale = ContentScale.Crop
        )
        Image(
            modifier = Modifier
                .size(64.dp)
                .scale(scale.value),
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.logo),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun SizeSelector(
    modifier: Modifier = Modifier,
    selectedSize: CupSize = CupSize.MEDIUM,
    selectSize: (CupSize) -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(white, shape = RoundedCornerShape(100.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CupSize.entries.forEach {
            val background =
                animateColorAsState(if (selectedSize == it) brown else Color.Transparent)
            val textColor = animateColorAsState(
                if (selectedSize == it) Color.White.copy(.87f) else darkGray.copy(.6f)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(background.value, shape = CircleShape)
                    .clickable { selectSize(it) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it.name.first().toString(),
                    style = TextStyle(
                        color = textColor.value,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.25.sp,
                        fontFamily = urbanist,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
private fun PercentageSelector(
    modifier: Modifier = Modifier,
    selectedPercentage: CoffeePercentage = CoffeePercentage.MEDIUM,
    selectPercentage: (CoffeePercentage) -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .background(white, shape = RoundedCornerShape(100.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CoffeePercentage.entries.forEach {
                val background =
                    animateColorAsState(if (selectedPercentage == it) brown else Color.Transparent)
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .background(background.value, shape = CircleShape)
                        .padding(8.dp)
                        .clickable {
                            selectPercentage(it)
                        },
                    painter = painterResource(R.drawable.coffee_bean),
                    contentDescription = stringResource(R.string.coffee_bean)
                )
            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(19.dp)
        ) {
            CoffeePercentage.entries.forEach {
                Text(
                    text = it.name.lowercase(),
                    style = TextStyle(
                        color = darkGray.copy(.6f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.25.sp,
                        fontFamily = urbanist,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
fun AlmostSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.almost_done),
            style = TextStyle(
                color = darkGray.copy(.87f),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = stringResource(R.string.your_coffee_will_be_finish_in),
            style = TextStyle(
                color = darkGray.copy(.6f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "CO\tFF\tEE",
            style = TextStyle(
                color = brown,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.25.sp,
                fontFamily = sniglet,
                textAlign = TextAlign.Center
            )
        )
    }
}