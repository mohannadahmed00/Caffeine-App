package com.giraffe.caffeineapp.screen.coffeetype

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.composable.BaseScreen
import com.giraffe.caffeineapp.composable.ProfileHeaderSection
import com.giraffe.caffeineapp.ui.theme.CaffeineAppTheme
import com.giraffe.caffeineapp.ui.theme.darkGray
import com.giraffe.caffeineapp.ui.theme.gray
import com.giraffe.caffeineapp.ui.theme.lightGray
import com.giraffe.caffeineapp.ui.theme.sniglet
import com.giraffe.caffeineapp.ui.theme.urbanist

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CoffeeTypeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToCupSizeScreen: (coffeeName: String) -> Unit = {}
) {
    CoffeeTypeContent(animatedVisibilityScope, navigateToCupSizeScreen)
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CoffeeTypeContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToCupSizeScreen: (coffeeName: String) -> Unit
) {
    val pagerState = rememberPagerState(2) { CoffeeType.entries.size }
    BaseScreen(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        buttonText = stringResource(R.string.continue_txt),
        buttonIconRes = R.drawable.right_arrow,
        header = {
            ProfileHeaderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
        },
        onButtonClick = { navigateToCupSizeScreen(CoffeeType.entries[pagerState.currentPage].name.lowercase()) }
    ) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(56.dp)
        ) {
            MorningSection(modifier = Modifier.padding(horizontal = 16.dp))
            CoffeeTypesSection(
                modifier = Modifier.height(298.dp),
                pagerState = pagerState,
                animatedVisibilityScope
            )
        }
    }
}

@Composable
fun MorningSection(modifier: Modifier = Modifier, username: String = "Hamsa") {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.good_morning),
            style = TextStyle(
                color = lightGray,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "$username ☀",
            style = TextStyle(
                color = gray,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Center
            )
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.what_would_you_like_to_drink_today),
            style = TextStyle(
                color = darkGray.copy(.8f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.25.sp,
                fontFamily = urbanist,
                textAlign = TextAlign.Center
            )
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.CoffeeTypesSection(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    HorizontalPager(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 60.dp),
        pageSpacing = (-60).dp,
        state = pagerState
    ) { pageIndex ->
        CoffeeTypeItem(
            modifier = Modifier.fillMaxWidth(),
            coffeeType = CoffeeType.entries[pageIndex],
            isSelected = pageIndex == pagerState.currentPage,
            animatedVisibilityScope
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.CoffeeTypeItem(
    modifier: Modifier = Modifier,
    coffeeType: CoffeeType,
    isSelected: Boolean,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val animatedScale = animateFloatAsState(
        targetValue = if (isSelected) 1f else .61f
    )
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(animatedScale.value),
                painter = painterResource(coffeeType.imageRes),
                contentDescription = coffeeType.name
            )
            Image(
                modifier = Modifier
                    .size(66.dp)
                    .offset(y = 20.dp)
                    .scale(animatedScale.value),
                painter = painterResource(R.drawable.logo),
                contentDescription = coffeeType.name
            )
        }
        Text(
            modifier = Modifier
                .sharedElement(
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedContentState = rememberSharedContentState(
                        key = "text-${coffeeType.name.lowercase()}"
                    ),
                ),
            text = coffeeType.name.lowercase(),
            style = TextStyle(
                color = darkGray,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 50.sp,
                letterSpacing = 0.25.sp,
                fontFamily = sniglet,
                textAlign = TextAlign.Center
            )
        )
    }
}

enum class CoffeeType(val imageRes: Int = R.drawable.black) {
    BLACK(R.drawable.black),
    LATTE(R.drawable.latte),
    MACCHIATO(R.drawable.macchiato),
    ESPRESSO(R.drawable.espresso),
}


@Preview
@Composable
private fun Preview() {
    CaffeineAppTheme {
        //CoffeeTypeScreen()
    }
}