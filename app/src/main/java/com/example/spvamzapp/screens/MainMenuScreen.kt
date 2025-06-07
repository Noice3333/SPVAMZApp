package com.example.spvamzapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.character.CharacterCard
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.viewmodels.EditViewModel
import com.example.spvamzapp.viewmodels.MainMenuViewModel


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
                title = { Text("Main menu") },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    Box() {
                        IconButton(onClick = {menuExpanded = true}) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Theme menu")
                        }
                        DropdownMenu(expanded = menuExpanded,
                            onDismissRequest = {menuExpanded = false}) {
                            DropdownMenuItem(text = {Text("Theme: Blue")},
                                onClick = {mmvm.changeTheme(1)})
                            DropdownMenuItem(text = {Text("Theme: Red")},
                                onClick = {mmvm.changeTheme(2)})
                            DropdownMenuItem(text = {Text("Theme: Green")},
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
                            contentDescription = "Create new character"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        MainMenu(Modifier.padding(innerPadding), state.charList, onEditButtonClicked, edcm, mmvm) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(modifier: Modifier = Modifier, list: List<CharacterEntry>,
             onEditButtonClicked: () -> Unit, edcm: EditViewModel,
             mainMenuViewModel: MainMenuViewModel
) {
    val layoutDirection = LocalLayoutDirection.current
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
        LazyColumn {
            items(list.size) { character ->
                CharacterCard(Modifier.padding(8.dp), list[character], onEditButtonClicked,
                    edcm, mainMenuViewModel)
            }
        }
    }
}