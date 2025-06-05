package com.example.spvamzapp.mainMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.character.CharacterEntry

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
                title = { Text("Create character") },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = {onBackButtonClicked()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { innerPadding -> CreateCharacter(Modifier.padding(innerPadding), mmvm, {onBackButtonClicked()}) }
}

@Composable
fun CreateCharacter(modifier: Modifier = Modifier, mmvm: MainMenuViewModel,
                    onCancelButtonClick: () -> Unit) {
    var characterName by rememberSaveable { mutableStateOf("") }
    val innerModifier = Modifier.padding(4.dp)
    Box(modifier = modifier) {
        Column(innerModifier) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Character name:") },
                value = characterName,
                onValueChange = { characterName = it }
            )
            Row(innerModifier) {
                Button(onClick = {onCancelButtonClick()}, Modifier) { Text(text = "Cancel") }
                Spacer(Modifier.weight(4f))
                Button(onClick = {
                    mmvm.addCharacter(CharacterEntry(name = characterName))
                    onCancelButtonClick()
                }) { Text(text = "Create") }
            }
        }
    }
}