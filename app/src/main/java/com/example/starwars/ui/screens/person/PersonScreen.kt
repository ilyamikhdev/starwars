package com.example.starwars.ui.screens.person

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.starwars.R
import com.example.starwars.domain.models.HomeworldModel
import com.example.starwars.domain.models.PersonExtendedModel
import com.example.starwars.ui.common.UIState
import com.example.starwars.ui.dialogs.PersonDetailsDialog
import com.example.starwars.ui.theme.ColorTransparent
import com.example.starwars.ui.theme.ColorWhite
import com.example.starwars.ui.theme.StarwarsTheme
import com.example.starwars.ui.widgets.AppSurface
import com.example.starwars.ui.widgets.StatusPlaceholder
import com.example.starwars.ui.widgets.TextWithLink

@Composable
fun PersonScreen(viewModel: PersonViewModel, onBack: (() -> Unit)) {
    val personState = viewModel.screenState.collectAsState().value

    when (personState.state) {
        UIState.LOADING -> {
            StatusPlaceholder(stringResource(R.string.loading))
        }

        UIState.ERROR -> {
            StatusPlaceholder(stringResource(R.string.error, personState.error ?: ""))
        }

        UIState.DATA -> {
            PersonScreen(
                onBack = onBack,
                onOpenDialog = { viewModel.showDialog(true) },
                onCloseDialog = { viewModel.showDialog(false) },
                showDialog = personState.showDialog,
                person = personState.data,
            )
        }
    }


}

@Composable
fun PersonScreen(
    onBack: (() -> Unit),
    onOpenDialog: (() -> Unit),
    onCloseDialog: (() -> Unit),
    showDialog: Boolean,
    person: PersonExtendedModel?,
) {
    Scaffold(
        containerColor = ColorTransparent,
        topBar = { TopBar(onBack) },
        bottomBar = {
            if (showDialog) {
                PersonDetailsDialog(onCloseDialog, person)
            }
        },
        content = { padding ->
            TextWithLink(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                name = person?.name ?: "",
                onTextClick = onOpenDialog
            )
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onBack: (() -> Unit)) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    tint = ColorWhite,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.title_people),
                color = ColorWhite,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorTransparent
        )
    )
}

@Preview
@Composable
fun PersonDetailsPreview() {
    StarwarsTheme {
        AppSurface {
            PersonScreen(
                onBack = {},
                onOpenDialog = {},
                onCloseDialog = {},
                showDialog = false,
                PersonExtendedModel(
                    id = "",
                    name = "Name",
                    homeworld = HomeworldModel(
                        id = "",
                        name = "Homeworld2",
                        population = 450000.00,
                        gravity = "1 standard",
                        orbitalPeriod = 312,
                        rotationPeriod = 26,
                        surfaceWater = 12.00
                    ),
                ),
            )
        }
    }
}