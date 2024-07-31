package com.example.starwars.ui.screens.people

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.starwars.R
import com.example.starwars.domain.models.PersonModel
import com.example.starwars.nav.Routes
import com.example.starwars.ui.common.UIState
import com.example.starwars.ui.theme.ColorFF1E1266
import com.example.starwars.ui.theme.ColorTransparent
import com.example.starwars.ui.theme.ColorWhite
import com.example.starwars.ui.theme.StarwarsTheme
import com.example.starwars.ui.widgets.CustomSurface

@Composable
fun PeopleScreen(viewModel: PeopleViewModel, navController: NavController) {
    val peopleState = viewModel.screenState.collectAsState().value
    ContentScreen(
        onClick = { id ->
            navController.navigate(Routes.Person.addArg(id))
        },
        peopleState = peopleState
    )
}

@Composable
fun ContentScreen(
    onClick: ((String) -> Unit)? = null,
    peopleState: PeopleState
) {
    Scaffold(
        containerColor = ColorTransparent,
        content = { innerPadding ->
            when (peopleState.state) {
                UIState.LOADING -> {
                    LabelStatus(stringResource(R.string.loading))
                }

                UIState.ERROR -> {
                    LabelStatus(stringResource(R.string.error, peopleState.error ?: ""))
                }

                UIState.DATA -> {
                    PeopleList(
                        contentPadding = innerPadding,
                        onClick = onClick,
                        data = peopleState.data
                    )
                }
            }

        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PeopleList(
    contentPadding: PaddingValues,
    onClick: ((String) -> Unit)? = null,
    data: List<PersonModel>
) {
    LazyColumn(
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorFF1E1266),
            ) {
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.title_people),
                        style = MaterialTheme.typography.headlineLarge,
                        color = ColorWhite,
                    )
                    HorizontalDivider(color = ColorWhite.copy(alpha = 0.3f))
                }
            }
        }
        itemsIndexed(data) { index, item ->
            PersonItem(item, onClick)
            if (index < data.lastIndex)
                HorizontalDivider(Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun PersonItem(person: PersonModel, onClick: ((String) -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onClick?.invoke(person.id)
            },
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Column {
            Label(text = person.name ?: "")
            Label(text = stringResource(R.string.height, person.height ?: 0))
            Label(text = stringResource(R.string.mass, person.mass ?: 0.0))
        }
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            tint = ColorWhite,
            contentDescription = null
        )


    }

}

@Composable
fun Label(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = ColorWhite,
    )
}

@Composable
fun LabelStatus(text: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Label(text = text)
    }
}

@Preview
@Composable
fun ContentScreenPreview() {
    StarwarsTheme {
        CustomSurface {
            ContentScreen(
                {},
                PeopleState(
                    state = UIState.DATA,
                    data = listOf(
                        PersonModel(id = "ID", name = "Name", height = 19, mass = 100.1),
                        PersonModel(id = "ID", name = "Name", height = 19, mass = 100.1),
                        PersonModel(id = "ID", name = "Name", height = 19, mass = 100.1),
                    ),
                )
            )
        }
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    StarwarsTheme {
        CustomSurface {
            ContentScreen(
                {},
                PeopleState(
                    state = UIState.LOADING
                )
            )
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    StarwarsTheme {
        CustomSurface {
            ContentScreen(
                {},
                PeopleState(
                    state = UIState.ERROR,
                    error = "No valid data"
                )
            )
        }
    }
}
