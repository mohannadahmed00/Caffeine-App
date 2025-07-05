package com.giraffe.caffeineapp.screen.start

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.giraffe.caffeineapp.composable.ProfileHeaderSection
import com.giraffe.caffeineapp.ui.theme.CaffeineAppTheme
import com.giraffe.caffeineapp.ui.theme.darkGray
import com.giraffe.caffeineapp.ui.theme.offWhite
import com.giraffe.caffeineapp.ui.theme.sniglet

@Composable
fun StartScreen(
    navigateToCoffeeTypeScreen: () -> Unit = {}
) {
    StartContent(navigateToCoffeeTypeScreen = navigateToCoffeeTypeScreen)
}

@Composable
private fun StartContent(
    navigateToCoffeeTypeScreen: () -> Unit
) {
    BaseScreen(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(offWhite)
            .verticalScroll(rememberScrollState()),
        buttonText = stringResource(R.string.bring_my_coffee),
        buttonIconRes = R.drawable.cup_of_coffee,
        onButtonClick = navigateToCoffeeTypeScreen,
        header = { ProfileHeaderSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) },
    ) {
        HocusPocusSection(modifier = Modifier
                .padding(horizontal = 58.dp)
                .padding(top = 24.dp))
        Ghost()
    }
}

@Composable
private fun Ghost() {
    val infiniteTransition = rememberInfiniteTransition()
    val ghostOffsetY by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = (-15).dp,
        animationSpec =
            InfiniteRepeatableSpec(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
        typeConverter = Dp.VectorConverter,
    )
    Column {
        Image(
            modifier = Modifier
                .padding(top = 13.dp)
                .offset(y = ghostOffsetY)
                .size(244.dp),
            painter = painterResource(R.drawable.ghost),
            contentDescription = stringResource(R.string.ghost)
        )
    }
}

@Composable
private fun HocusPocusSection(modifier: Modifier = Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Bottom),
                painter = painterResource(R.drawable.thin_star),
                contentDescription = stringResource(R.string.star)
            )
            Text(
                text = "Hocus Pocus",
                style = TextStyle(
                    color = darkGray.copy(.87f),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 50.sp,
                    letterSpacing = 0.25.sp,
                    fontFamily = sniglet,
                    textAlign = TextAlign.Center
                )
            )
            Image(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(R.drawable.thin_star),
                contentDescription = stringResource(R.string.star)
            )
        }
        Column {
            Text(
                text = "I Need Coffee to Focus",
                style = TextStyle(
                    color = darkGray.copy(.87f),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 50.sp,
                    letterSpacing = 0.25.sp,
                    fontFamily = sniglet,
                    textAlign = TextAlign.Center
                )
            )
            Image(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.End),
                painter = painterResource(R.drawable.thin_star),
                contentDescription = stringResource(R.string.star)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CaffeineAppTheme {
        StartScreen()
    }
}