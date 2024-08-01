package com.example.starwars.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.starwars.R
import com.example.starwars.domain.models.HomeworldModel
import com.example.starwars.domain.models.PersonExtendedModel
import com.example.starwars.ui.theme.StarwarsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsDialog(
    onCloseDialog: (() -> Unit),
    person: PersonExtendedModel?
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier
            .fillMaxHeight(0.4f),
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                onCloseDialog.invoke()
            }
        },
        sheetState = sheetState
    ) {
        Content(person)
    }
}

@Composable
fun Content(person: PersonExtendedModel?) {
    Column(Modifier.padding(16.dp)) {
        Text(person?.name ?: "...", style = MaterialTheme.typography.headlineMedium)
        Text(stringResource(id = R.string.home_world, person?.homeworld?.name ?: ""))
        Text(stringResource(id = R.string.population, person?.homeworld?.population ?: ""))
        Text(stringResource(id = R.string.gravity, person?.homeworld?.gravity ?: ""))
        Text(stringResource(id = R.string.orbitalPeriod, person?.homeworld?.orbitalPeriod ?: ""))
        Text(stringResource(id = R.string.rotationPeriod, person?.homeworld?.rotationPeriod ?: ""))
        Text(stringResource(id = R.string.surfaceWater, person?.homeworld?.surfaceWater ?: ""))
    }
}

@Preview
@Composable
fun ContentPreview() {
    StarwarsTheme {
        Content(
            person = PersonExtendedModel(
                id = "1",
                name = "Luke Skywalker",
                homeworld = HomeworldModel(
                    id = "id",
                    name = "Tatooine",
                    population = 120000.00,
                    gravity = "1 standard",
                    orbitalPeriod = 12,
                    rotationPeriod = 13,
                    surfaceWater = 1.00,
                )
            )
        )
    }
}