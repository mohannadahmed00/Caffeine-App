package com.giraffe.caffeineapp.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    buttonText: String,
    buttonIconRes: Int,
    header: @Composable () -> Unit,
    onButtonClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,

    ) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header()
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
        DefaultButton(
            modifier = Modifier.padding(bottom = 50.dp),
            text = buttonText,
            painter = painterResource(buttonIconRes),
            onClick = onButtonClick
        )
    }
}