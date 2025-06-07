package com.example.spvamzapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.spvamzapp.character.CharacterEntry
import com.example.spvamzapp.item.Item
import com.example.spvamzapp.item.ItemCard
import com.example.spvamzapp.spell.Spell
import com.example.spvamzapp.spell.SpellCard
import com.example.spvamzapp.viewmodels.EditViewModel
import com.example.spvamzapp.viewmodels.MainMenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCharacterScreen(
    mmvm: MainMenuViewModel,
    edcm: EditViewModel,
    onBackButtonClicked: () -> Unit
) {
    var selectedNavBarIcon by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text("Edit character") },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = {onBackButtonClicked()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Return to main menu"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedNavBarIcon == 0,
                    onClick = {
                        if (selectedNavBarIcon != 0) {
                            selectedNavBarIcon = 0
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.Person,
                            contentDescription = "Edit character")
                    }
                )
                NavigationBarItem(
                    selected = selectedNavBarIcon == 1,
                    onClick = {
                        if (selectedNavBarIcon != 1) {
                            selectedNavBarIcon = 1
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.MailOutline,
                            contentDescription = "Edit character")
                    }
                )
                NavigationBarItem(
                    selected = selectedNavBarIcon == 2,
                    onClick = {
                        if (selectedNavBarIcon != 2) {
                            selectedNavBarIcon = 2
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Filled.Star,
                            contentDescription = "Edit character")
                    }
                )
            }
        }
    ) { innerPadding ->
        when (selectedNavBarIcon) {
            0 -> {
                EditCharacter(Modifier.padding(innerPadding),
                    mmvm, edcm,
                    { onBackButtonClicked() })
            }
            1 -> { EditItems(Modifier.padding(innerPadding),
                    edcm)
            }
            2 -> {
                EditSpells(Modifier.padding(innerPadding),
                    edcm)
            }
        }
    }
}

@Composable
fun EditCharacter(modifier: Modifier = Modifier, mmvm: MainMenuViewModel,
                  edcm: EditViewModel,
                  onCancelButtonClick: () -> Unit) {
    var characterName by rememberSaveable { mutableStateOf(edcm.selectedChar?.name) }
    val innerModifier = Modifier.padding(4.dp)

    Box(modifier = modifier.fillMaxSize()) {
        Column(innerModifier) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Character name:") },
                value = characterName ?: "",
                onValueChange = { characterName = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
            Row(innerModifier) {
                Button(onClick = { onCancelButtonClick() }, Modifier) { Text(text = "Cancel") }
                Spacer(Modifier.weight(4f))
                Button(onClick = {
                    edcm.selectedChar?.name = characterName ?: ""
                    mmvm.editCharacter(edcm.selectedChar ?: CharacterEntry())
                    onCancelButtonClick()
                }) { Text(text = "Save") }
            }
        }
        FloatingActionButton(
            onClick = {
                mmvm.removeCharacter(edcm.selectedChar ?: CharacterEntry())
                onCancelButtonClick()
                      },
            containerColor = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        { Icon(imageVector = Icons.Filled.Clear, contentDescription = "",
            tint = MaterialTheme.colorScheme.onErrorContainer) }
    }
}

@Composable
fun EditItems(modifier: Modifier = Modifier,
              edcm: EditViewModel
) {
    val myFlow = edcm.itemFlowList.collectAsState(listOf())
    Column(modifier) {
        Button(onClick = {
            edcm.addItem(Item(charId = edcm.selectedChar?.id ?: 0))
        }) { Text(text = "Add blank item")}
        LazyColumn {
            items(myFlow.value.size) { item ->
                ItemCard(Modifier.padding(4.dp), edcm, myFlow.value[item])
            }
        }
    }
}

@Composable
fun EditSpells(modifier: Modifier = Modifier,
               edcm: EditViewModel) {
    val myFlow = edcm.spellFlowList.collectAsState(listOf())
    Column(modifier) {
        Button(onClick = {
            edcm.addSpell(Spell(charId = edcm.selectedChar?.id ?: 0))
        }) { Text(text = "Add blank item")}
        LazyColumn {
            items(myFlow.value.size) { spell ->
                SpellCard(Modifier.padding(4.dp), edcm, myFlow.value[spell])
            }
        }
    }
}