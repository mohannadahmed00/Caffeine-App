package com.giraffe.caffeineapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giraffe.caffeineapp.R
import com.giraffe.caffeineapp.ui.theme.darkGray
import com.giraffe.caffeineapp.ui.theme.urbanist

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.bring_my_coffee),
    painter: Painter = painterResource(R.drawable.cup_of_coffee),
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(100.dp),
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
        colors = ButtonDefaults.buttonColors().copy(containerColor = darkGray),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    color = Color.White.copy(.87f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.25.sp,
                    fontFamily = urbanist
                )
            )
            Image(
                modifier = Modifier.size(24.dp),
                painter = painter,
                contentDescription = stringResource(R.string.cup_of_coffee)
            )
        }
    }
}