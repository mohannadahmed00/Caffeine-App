package com.giraffe.caffeineapp.screen.cupsize

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.composable.BaseScreen
import com.giraffe.caffeineapp.screen.coffeetype.CoffeeType
import com.giraffe.caffeineapp.ui.theme.CaffeineAppTheme
import com.giraffe.caffeineapp.ui.theme.brown
import com.giraffe.caffeineapp.ui.theme.darkGray
import com.giraffe.caffeineapp.ui.theme.urbanist
import com.giraffe.caffeineapp.ui.theme.white
import org.koin.androidx.compose.koinViewModel

@Composable
fun CupSizeScreen(
    viewModel: CupSizeViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    Content(state, viewModel, onBackClick)
}

@Composable
private fun Content(
    state: CoffeeSizeScreenState,
    interaction: CupSizeScreenInteraction,
    onBackClick: () -> Unit,
) {
    BaseScreen(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        buttonText = stringResource(R.string.continue_txt),
        buttonIconRes = R.drawable.right_arrow,
        header = {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                onBackClick = onBackClick
            )
        },
        onButtonClick = {}
    ) {
        CupSection(
            modifier = Modifier
                .padding(top = 37.dp, end = 10.dp, start = 16.dp)
                .fillMaxWidth()
                .weight(1f),
            cupSize = state.selectedSize,
            selectedPercentage = state.selectedPercentage
        )
        SizeSelector(
            modifier = Modifier.padding(top = 27.dp),
            selectedSize = state.selectedSize,
            selectSize = interaction::selectSize
        )
        PercentageSelector(
            modifier = Modifier.padding(top = 32.dp, bottom = 92.dp),
            selectedPercentage = state.selectedPercentage,
            selectPercentage = interaction::selectPercentage
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    coffeeType: CoffeeType = CoffeeType.MACCHIATO,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onBackClick),
            painter = painterResource(R.drawable.arrow),
            contentDescription = stringResource(R.string.back_arrow),
            tint = darkGray.copy(.87f)
        )
        Text(
            text = coffeeType.name.lowercase(),
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
    val scale = animateFloatAsState(
        when (cupSize) {
            CupSize.SMALL -> .8f
            CupSize.MEDIUM -> .9f
            CupSize.LARGE -> 1f
        }
    )
    val coffeeOffsetY = remember { Animatable(50.dp, Dp.VectorConverter) }
    var shotsCount by rememberSaveable { mutableIntStateOf(0) }


    LaunchedEffect(selectedPercentage) {
        when (selectedPercentage) {
            CoffeePercentage.LOW -> {
                if (shotsCount > 1) {
                    while (shotsCount > 1) {
                        coffeeOffsetY.snapTo(50.dp)
                        coffeeOffsetY.animateTo((-1000).dp, animationSpec = tween(400))
                        shotsCount--
                    }
                } else {
                    while (shotsCount < 1) {
                        coffeeOffsetY.snapTo((-1000).dp)
                        coffeeOffsetY.animateTo(50.dp, animationSpec = tween(400))
                        shotsCount++
                    }
                }
            }

            CoffeePercentage.MEDIUM -> {
                if (shotsCount > 2) {
                    while (shotsCount > 2) {
                        coffeeOffsetY.snapTo(50.dp)
                        coffeeOffsetY.animateTo((-1000).dp, animationSpec = tween(400))
                        shotsCount--
                    }

                } else {
                    while (shotsCount < 2) {
                        coffeeOffsetY.snapTo((-1000).dp)
                        coffeeOffsetY.animateTo(50.dp, animationSpec = tween(400))
                        shotsCount++
                    }
                }
            }

            CoffeePercentage.HIGH -> {
                while (shotsCount < 3) {
                    coffeeOffsetY.snapTo((-1000).dp)
                    coffeeOffsetY.animateTo(50.dp, animationSpec = tween(400))
                    shotsCount++
                }
            }
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier
                .padding(vertical = 63.dp)
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
                .scale(scale.value)
                .offset(y = coffeeOffsetY.value)
                .align(Alignment.TopCenter),
            painter = painterResource(R.drawable.coffee_beans),
            contentDescription = stringResource(R.string.cup),
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

@Preview
@Composable
private fun Preview() {
    CaffeineAppTheme {
        CupSizeScreen {}
    }
}