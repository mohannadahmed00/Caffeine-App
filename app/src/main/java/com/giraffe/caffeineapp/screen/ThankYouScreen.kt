package com.giraffe.caffeineapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.composable.DefaultButton
import com.giraffe.caffeineapp.screen.snack.Snack
import com.giraffe.caffeineapp.ui.theme.brown
import com.giraffe.caffeineapp.ui.theme.darkGray
import com.giraffe.caffeineapp.ui.theme.urbanist

@Composable
fun ThankYouScreen(snack: Snack) {
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
                .align(Alignment.Start),
            imageVector = Icons.Rounded.Close,
            tint = darkGray.copy(.87f),
            contentDescription = stringResource(R.string.close)
        )
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        )
        {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(R.drawable.coffee_bean),
                tint = brown,
                contentDescription = stringResource(R.string.coffee_bean)
            )
            Text(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(R.string.more_espresso_less_depresso),
                style = TextStyle(
                    color = darkGray.copy(.87f),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.25.sp,
                    fontFamily = urbanist,
                    textAlign = TextAlign.Start
                )
            )
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(R.drawable.coffee_bean),
                tint = brown,
                contentDescription = stringResource(R.string.coffee_bean)
            )
        }
        Image(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
            painter = painterResource(snack.image),
            contentDescription = snack.name,
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            Text(
                text = stringResource(R.string.bon_app_tit),
                style = TextStyle(
                    color = darkGray.copy(.8f),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.25.sp,
                    fontFamily = urbanist,
                    textAlign = TextAlign.Start
                )
            )
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(R.drawable.star),
                tint = darkGray.copy(.8f),
                contentDescription = stringResource(R.string.coffee_bean)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        DefaultButton(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.thank_youuu),
            painter = painterResource(R.drawable.right_arrow),
            onClick = { }
        )
    }
}