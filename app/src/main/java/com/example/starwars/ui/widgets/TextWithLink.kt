package com.example.starwars.ui.widgets

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.starwars.R
import com.example.starwars.ui.theme.ColorBlue
import com.example.starwars.ui.theme.ColorWhite
import com.example.starwars.ui.theme.StarwarsTheme

const val CLICK_TAG = "click"

@Composable
fun TextWithLink(
    modifier: Modifier = Modifier,
    name: String,
    onTextClick: () -> Unit
) {
    val clickableText = stringResource(R.string.here)
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.click))
        pushStringAnnotation(tag = CLICK_TAG, annotation = CLICK_TAG)
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = ColorBlue
            )
        ) {
            append(clickableText)
        }
        pop()
        append(stringResource(R.string.to_view_homeworld_data_for, name))
    }

    ClickableText(
        modifier = modifier,
        text = annotatedText,
        style = TextStyle(
            color = ColorWhite,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize
        ),
        onClick = { offset ->
            val annotation = annotatedText.getStringAnnotations(
                tag = CLICK_TAG,
                start = offset,
                end = offset
            ).firstOrNull()

            if (annotation != null) {
                onTextClick()
            }
        }
    )
}

@Preview
@Composable
fun ContentPreview() {
    StarwarsTheme {
        TextWithLink(
            name = "Luke Skywalker",
            onTextClick = {}
        )
    }
}