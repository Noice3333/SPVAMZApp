package com.example.spvamzapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.spvamzapp.R
import com.example.spvamzapp.character.CharacterCard
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.viewmodels.EditViewModel
import com.example.spvamzapp.viewmodels.MainMenuViewModel
import kotlin.random.Random

//Hlavná obrazovka, na ktorej sa ocitne používaťeľ po otvorení aplikácie
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    mmvm: MainMenuViewModel,
    edcm: EditViewModel,
    onEditButtonClicked: () -> Unit,
    onCreateButtonClicked: () -> Unit
) {
    val state by mmvm.mainMenuUiState.collectAsState()
    var menuExpanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text(stringResource(R.string.main_menu_title)) },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    Box {
                        IconButton(onClick = {menuExpanded = true}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = stringResource(
                                R.string.theme_menu_desc
                            ))
                        }
                        DropdownMenu(expanded = menuExpanded,
                            onDismissRequest = {menuExpanded = false}) {
                            DropdownMenuItem(text = {Text(stringResource(R.string.theme_blue))},
                                onClick = {mmvm.changeTheme(1)})
                            DropdownMenuItem(text = {Text(stringResource(R.string.theme_red))},
                                onClick = {mmvm.changeTheme(2)})
                            DropdownMenuItem(text = {Text(stringResource(R.string.theme_green))},
                                onClick = {mmvm.changeTheme(3)})
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onCreateButtonClicked()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.create_new_character_button_desc)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        MainMenu(Modifier.padding(innerPadding), state.charList, onEditButtonClicked, edcm, mmvm) }
}

//Obsah hlavného menu - zobrazuje zoznam postáv
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(modifier: Modifier = Modifier, list: List<CharacterEntry>,
             onEditButtonClicked: () -> Unit, edcm: EditViewModel,
             mainMenuViewModel: MainMenuViewModel
) {
    val layoutDirection = LocalLayoutDirection.current
    var diceRoll by rememberSaveable { mutableStateOf(false) }

    Surface(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(
            start = WindowInsets.safeDrawing.asPaddingValues()
                .calculateStartPadding(layoutDirection),
            end = WindowInsets.safeDrawing.asPaddingValues()
                .calculateEndPadding(layoutDirection)
        )
    ){
        Box {
            if (diceRoll) DiceRollDialog { diceRoll = false}
            LazyColumn {
                items(list.size) { character ->
                    CharacterCard(
                        Modifier.padding(8.dp), list[character], onEditButtonClicked,
                        edcm, mainMenuViewModel
                    )
                }
            }
            FloatingActionButton(modifier = Modifier.align(Alignment.BottomStart).padding(4.dp),
                onClick = { diceRoll = true }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = stringResource(R.string.dice_roll_dialog_desc)
                )
            }
        }
    }
}

//Dialógové okno na "hod kockou"
@Composable
fun DiceRollDialog(
    onDismissRequest: () -> Unit,
) {
    var lowerBound by rememberSaveable { mutableStateOf("1") }
    var lowerBoundCorrect by rememberSaveable { mutableStateOf(true) }
    var upperBound by rememberSaveable { mutableStateOf("6") }
    var upperBoundCorrect by rememberSaveable { mutableStateOf(true) }
    var result by rememberSaveable { mutableStateOf("0") }
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box {
            Card(Modifier
                .height(300.dp)
                .width(200.dp)
                .padding(8.dp)) {
                Column {
                    Text(text = stringResource(R.string.dice_roll), modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(8.dp), style = MaterialTheme.typography.headlineSmall)
                    Text(text = stringResource(R.string.dice_roll_subtext),
                        modifier = Modifier
                            .align(
                                Alignment.CenterHorizontally
                            )
                            .padding(4.dp), textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium)
                    Row(Modifier.weight(2f)) {
                        TextField(
                            modifier = Modifier.weight(1f).padding(4.dp),
                            value = lowerBound.toString(),
                            onValueChange = { lowerBound = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            isError = !lowerBoundCorrect
                        )
                        TextField(
                            modifier = Modifier.weight(1f).padding(4.dp),
                            value = upperBound.toString(),
                            onValueChange = { upperBound = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            isError = !upperBoundCorrect
                        )
                    }
                    val lowerBoundInt = lowerBound.toIntOrNull()
                    val upperBoundInt = upperBound.toIntOrNull()
                    Text(text = result, Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(1f))
                    TextButton(onClick = {
                        lowerBoundCorrect = lowerBoundInt != null
                        upperBoundCorrect = upperBoundInt != null
                        if (lowerBoundInt != null && upperBoundInt != null) {
                            result = if (lowerBoundInt <= upperBoundInt)
                                Random.nextInt(lowerBoundInt, upperBoundInt + 1)
                                    .toString()
                            else
                                Random.nextInt(upperBoundInt, lowerBoundInt + 1)
                                    .toString()
                        }
                    },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                ) { Text(stringResource(R.string.roll_button_text)) }
                }
            }
        }
    }
}