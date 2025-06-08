package com.example.spvamzapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.R
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.viewmodels.MainMenuViewModel

//Obrazovka, na ktorej sa dá vytvoriť nová postava
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCharacterScreen(
    mmvm: MainMenuViewModel,
    onBackButtonClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text(stringResource(R.string.create_character_screen_title)) },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = {onBackButtonClicked()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_desc)
                        )
                    }
                }
            )
        }
    ) { innerPadding -> CreateCharacter(Modifier.padding(innerPadding), mmvm) { onBackButtonClicked() } }
}

//Obsah obrazovky, stará so o vytvorenie novej postavy
@Composable
fun CreateCharacter(modifier: Modifier = Modifier, mmvm: MainMenuViewModel,
                    onCancelButtonClick: () -> Unit) {
    var characterName by rememberSaveable { mutableStateOf("") }
    var characterRace by rememberSaveable { mutableStateOf("") }
    var characterRaceSwitch by rememberSaveable { mutableStateOf(false) }
    var characterRaceMenu by rememberSaveable { mutableStateOf(false) }
    var characterClass by rememberSaveable { mutableStateOf("") }
    var characterClassSwitch by rememberSaveable { mutableStateOf(false) }
    var characterClassMenu by rememberSaveable { mutableStateOf(false) }
    var characterAlignment by rememberSaveable { mutableStateOf("") }
    var characterAlignmentSwitch by rememberSaveable { mutableStateOf(false) }
    var characterAlignmentMenu by rememberSaveable { mutableStateOf(false) }
    val innerModifier = Modifier.padding(8.dp)
    Box(modifier = modifier) {
        Column(innerModifier) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text(text = stringResource(R.string.character_name_dummy)) },
                value = characterName,
                onValueChange = { characterName = it }
            )
            Row(innerModifier) {
                Box(Modifier.weight(6f)) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = characterRace,
                        onValueChange = { characterRace = it },
                        enabled = characterRaceSwitch,
                        label = { Text(stringResource(R.string.race_selection)) },
                        singleLine = true
                    )
                    IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { characterRaceMenu = true }) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.expand_race_menu_button_desc)
                        )
                    }
                    DropdownMenu(expanded = characterRaceMenu,
                        onDismissRequest = { characterRaceMenu = false }) {
                        stringArrayResource(R.array.characterRaces).forEach {
                            DropdownMenuItem(text = {Text(it)},
                                onClick = {characterRace = it
                                characterRaceMenu = false})
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    stringResource(R.string.custom_switch_text), Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp))
                Switch(modifier = Modifier.align(Alignment.CenterVertically),
                    checked = characterRaceSwitch,
                    onCheckedChange = { characterRaceSwitch = it })
            }
            Row(innerModifier) {
                Box(Modifier.weight(6f)) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = characterClass,
                        onValueChange = { characterClass = it },
                        enabled = characterClassSwitch,
                        label = { Text(stringResource(R.string.class_selection)) },
                        singleLine = true
                    )
                    IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { characterClassMenu = true }) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.expand_class_menu_desc)
                        )
                    }
                    DropdownMenu(expanded = characterClassMenu,
                        onDismissRequest = {characterClassMenu = false}) {
                        stringArrayResource(R.array.characterClasses).forEach {
                            DropdownMenuItem(text = {Text(it)},
                                onClick = {characterClass = it
                                characterClassMenu = false})
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    stringResource(R.string.custom_switch_text), Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp))
                Switch(modifier = Modifier.align(Alignment.CenterVertically),
                    checked = characterClassSwitch,
                    onCheckedChange = { characterClassSwitch = it })
            }
            Row(innerModifier) {
                Box(Modifier.weight(6f)) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = characterAlignment,
                        onValueChange = { characterAlignment = it },
                        enabled = characterAlignmentSwitch,
                        label = { Text(stringResource(R.string.alignment_selection)) },
                        singleLine = true
                    )
                    IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { characterAlignmentMenu = true}) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.expand_alignment_menu_desc)
                        )
                    }
                    DropdownMenu(expanded = characterAlignmentMenu,
                        onDismissRequest = {characterAlignmentMenu = false}) {
                        stringArrayResource(R.array.characterAlignments).forEach {
                            DropdownMenuItem(text = {Text(it)},
                                onClick = {characterAlignment = it
                                characterAlignmentMenu = false})
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    stringResource(R.string.custom_switch_text), Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp))
                Switch(modifier = Modifier.align(Alignment.CenterVertically),
                    checked = characterAlignmentSwitch,
                    onCheckedChange = { characterAlignmentSwitch = it })
            }
            Row(innerModifier) {
                Spacer(Modifier.weight(4f))
                Button(onClick = {
                    mmvm.addCharacter(CharacterEntry(name = characterName,
                        charRace = characterRace,
                        charClass = characterClass,
                        charAlignment = characterAlignment))
                    onCancelButtonClick()
                }) { Text(text = stringResource(R.string.create_button_text)) }
            }
        }
    }
}